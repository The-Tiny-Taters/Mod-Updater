package the_tiny_taters.mod_updater.command

import com.github.p03w.aegis.aegisCommand
import com.mojang.brigadier.arguments.StringArgumentType.getString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.minecraft.text.LiteralText
import net.minecraft.util.Formatting
import the_tiny_taters.mod_updater.data_fetchers.Fetchers
import the_tiny_taters.mod_updater.ext.toHashAlgorithm
import the_tiny_taters.mod_updater.mod_data.DirectMod
import the_tiny_taters.mod_updater.mod_data.IHash
import the_tiny_taters.mod_updater.mod_data.ModBase
import the_tiny_taters.mod_updater.mod_data.ModrinthMod
import the_tiny_taters.mod_updater.util.*

/**
 * /download (modrinth|curseforge|github|direct) <identifier>
 */
class DownloadCommand {
    /**
     * Registers the command
     */
    fun register(): Node {
        val downloadNode =
            aegisCommand("download") {
                requires {
                    it.hasPermissionLevel(4)
                }
            }
                .build()

        val directNode =
            aegisCommand("direct") {
                string("url") {
                    executes { GlobalScope.launch {
                        downloadMod(
                            it.source,
                            DirectMod(getString(it, "url")),
                        )
                    } }

                    word("hash-algorithm") {
                        suggests { _, builder ->
                            HashTypes.values().forEach { 
                                builder.suggest(it.toString())
                            }
                            
                            builder.buildFuture()
                        }

                        executes { GlobalScope.launch {
                            downloadMod(
                                it.source,
                                DirectMod(
                                    getString(it, "url"),
                                    getString(it, "hash-algorithm").toHashAlgorithm()
                                )
                            )
                        } }
                    }
                }
            }
                .build()

        val modrinthNode =
            aegisCommand("modrinth") {
                word("slug") {
                    executes { GlobalScope.launch {
                        fromModrinth(
                            it.source,
                            getString(it, "slug"),
                        )
                    } }

                    greedyString("version") {
                        executes { GlobalScope.launch {
                            fromModrinth(
                                it.source,
                                getString(it, "slug"),
                                getString(it, "version"),
                            )
                        } }
                    }
                }
            }
                .build()

        downloadNode.addChild(directNode)
        downloadNode.addChild(modrinthNode)

        return downloadNode
    }

    /**
     * Attempts to download mod from modrinth
     */
    private suspend fun fromModrinth(source: Source, slug: String, version: String? = null) {
        val mod = ModrinthMod(slug, version = version)
        val dataFetchJob = GlobalScope.async { Fetchers.MODRINTH.fetcher.fetchProperties(mod) }
        if (dataFetchJob.await() && mod.url != null) {
            downloadMod(source, mod)
        } else {
            source.sendError(
                LiteralText("§cError occurred while getting data from Modrinth!"),
            )
        }
    }

    /**
     * Attempts to download the mod, runs lots of async stuff. :concern:
     * Will check hash if possible.
     * Sends many messages to the [source].
     *
     * @param source the command source
     * @param mod the mod that is to be downloaded
     */
    private suspend fun downloadMod(source: Source, mod: ModBase) {
        source.sendFeedback(
            LiteralText("§eDownloading §9${mod.fileName}§e..."),
            false
        )

        val downloadJob = GlobalScope.async { FileManager.download(mod) }

        if (downloadJob.await()) {
            source.sendFeedback(
                LiteralText("§7Downloaded §8${mod.fileName}."),
                true
            )

            when (mod.source) {
                ModSources.MODRINTH -> {
                    check(mod is IHash)
                    
                    source.sendFeedback(
                        LiteralText("§7Calculating hash of downloaded file..."),
                        false
                    )

                    val hashCheckJob = GlobalScope.async { FileManager.checkHash(mod) }

                    if (hashCheckJob.await()) {
                        source.sendFeedback(
                            LiteralText("§aHashes match! §9${mod.name} §ahas been successfully downloaded."),
                            true
                        )
                    } else {
                        source.sendError(
                            LiteralText("§cHashes do not match, deleting downloaded file...")
                        )

                        val deleteFileJob = GlobalScope.async { FileManager.delete(mod) }

                        if (deleteFileJob.await()) {
                            source.sendFeedback(
                                LiteralText("§7Deleted §8${mod.fileName} §7because hashes did not match."),
                                true
                            )
                        } else {
                            source.sendError(
                                LiteralText("§cError occurred while deleting ${mod.fileName}!")
                            )
                        }
                    }
                }

                ModSources.DIRECT -> {
                    check(mod is IHash)

                    if (mod.hashType != null) {
                        val hashCheckJob = GlobalScope.async { FileManager.getFileChecksum(mod.hashType!!.digest, mod.toFile()) }

                        source.sendFeedback(
                            LiteralText(
                                "§eDownloaded file has §9${mod.hashType} §ehash: §9${hashCheckJob.await()}" +
                                        "\n§9${mod.name} §ahas been successfully downloaded."
                            ),
                            true
                        )
                    } else {
                        source.sendFeedback(
                            LiteralText(
                                        "§9${mod.name} §ahas been successfully downloaded." +
                                                "§cCannot verify hash of mods downloaded from direct links" +
                                                "§c, use with caution!"
                            ),
                            true
                        )
                    }
                }

                else -> {
                    source.sendError(
                        LiteralText("HOW")
                    )
                }
            }

        } else {
            source.sendError(
                LiteralText("§cDownload failed!")
            )
        }
    }
}