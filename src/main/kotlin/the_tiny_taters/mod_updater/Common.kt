package the_tiny_taters.mod_updater

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.loader.FabricLoader
import net.minecraft.SharedConstants
import the_tiny_taters.mod_updater.command.DownloadCommand
import the_tiny_taters.mod_updater.util.Dispatcher
import java.io.File

/**
 * Entrypoint and initial registration
 */
object Common : ModInitializer {
    val modsDir: File = FabricLoader.INSTANCE.modsDir.toFile()
    val mcVersion: String = SharedConstants.getGameVersion().name

    /**
     * Entrypoint
     */
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register(::registerCommands)
    }

    /**
     * Command registration
     */
    private fun registerCommands(dispatcher: Dispatcher, dedicated: Boolean) {
        dispatcher.root.addChild(DownloadCommand().register())
    }
}

