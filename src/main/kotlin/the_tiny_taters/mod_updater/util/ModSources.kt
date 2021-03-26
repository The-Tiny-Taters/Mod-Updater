package the_tiny_taters.mod_updater.util

/**
 * The supported mod download sources
 */
enum class ModSources(private val toString: String) {
    MODRINTH("Modrinth"),
    CURSEFORGE("CurseForge"),
    GITHUB("GitHub"),
    DIRECT("direct link"),;

    override fun toString(): String {
        return toString
    }
}