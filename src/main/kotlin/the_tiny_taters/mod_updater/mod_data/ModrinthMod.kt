package the_tiny_taters.mod_updater.mod_data

import the_tiny_taters.mod_updater.util.HashTypes
import the_tiny_taters.mod_updater.util.ModSources

/**
 * Mod that is hosted on Modrinth
 */
data class ModrinthMod(
    /**
     * The mod's slug on Modrinth
     */
    var slug: String,

    /**
     * The mod's project id on Modrinth
     */
    var projectId: String? = null,

    /**
     * The mod's version id on Modrinth
     */
    var versionId: String? = null,

    override var url: String? = null,

    override var version: String? = null,

    override var hash: String? = null,
)
: ModBase(source = ModSources.MODRINTH), IVersion, IHash {
    override val hashType: HashTypes = HashTypes.SHA1
}
