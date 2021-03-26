package the_tiny_taters.mod_updater.mod_data

import the_tiny_taters.mod_updater.Common.modsDir
import the_tiny_taters.mod_updater.util.ModSources
import java.io.File
import java.nio.file.FileSystems

/**
 * The base class for all mod data classes
 */
open class ModBase (
    /**
     * The method of getting the mod, see [ModSources]
     */
    val source: ModSources,

    /**
     * The download URL of the mod
     */
    open val url: String? = null,

    /**
     * Name of the mod file, includes `.jar`
     */
    var fileName: String? = null,

    /**
     * The mod's id
     */
    var modId: String? = null,

    /**
     * Formatted name of the mod
     */
    var name: String? = null,
) {
    fun toFile(): File {
        return FileSystems.getDefault().getPath("$modsDir/${this.fileName}").toFile()
    }
}