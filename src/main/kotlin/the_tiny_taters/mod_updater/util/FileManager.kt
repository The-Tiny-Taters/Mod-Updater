package the_tiny_taters.mod_updater.util

import the_tiny_taters.mod_updater.Common.modsDir
import the_tiny_taters.mod_updater.mod_data.IHash
import the_tiny_taters.mod_updater.mod_data.ModBase
import java.io.BufferedInputStream
import java.io.File
import java.lang.Exception
import java.net.URL
import java.nio.file.*
import java.security.MessageDigest
import java.io.FileInputStream

import kotlin.jvm.Throws

/**
 * Handles mod downloading
 */
object FileManager {
    /**
     * Attempts to download the requested mod
     *
     * @param link the url to the mod file
     * @return whether or not the download is successful
     */
    fun download(mod: ModBase): Boolean {
        try {
            val downloadStream = BufferedInputStream(URL(mod.url).openStream())
            Files.copy(
                downloadStream,
                FileSystems.getDefault().getPath("$modsDir/${mod.fileName}"),
                StandardCopyOption.REPLACE_EXISTING
            )

        } catch (e: Exception) {
            return false
        }

        return true
    }

    /**
     * Checks if the hash of the mod file matches the hash
     *
     * @param mod the mod that
     * @return whether or not the hashes match
     */
    fun <T> checkHash(mod: T): Boolean where T: ModBase, T: IHash {
        val localHash = getFileChecksum(
            mod.hashType!!.digest,
            FileSystems.getDefault().getPath("$modsDir/${mod.fileName}").toFile()
        )

        return localHash == mod.hash!!
    }

    /**
     * Deletes the requested mod
     *
     * @param mod the mod to delete
     * @return whether or not it was successfully deleted
     */
    fun delete(mod: ModBase): Boolean {
        val modPath = FileSystems.getDefault().getPath("$modsDir/${mod.fileName}")

        return Files.deleteIfExists(modPath)
    }

    /**
     * Calculates checksum of a specified file
     *
     * @param digest the hash algorithm to use
     * @param file the file to hash
     * @return the hash
     */
    @Throws(Exception::class)
    fun getFileChecksum(digest: MessageDigest, file: File): String {
        val inputStream = FileInputStream(file)
        val bytes = digest.digest(inputStream.readAllBytes())
        inputStream.close()

        val sb: StringBuilder = StringBuilder(bytes.size * 2)

        bytes.forEach {
            sb.append(String.format("%02X", it))
        }

        return sb.toString()
    }
}