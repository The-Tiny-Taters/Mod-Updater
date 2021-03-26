package the_tiny_taters.mod_updater.mod_data

import the_tiny_taters.mod_updater.util.HashTypes
import the_tiny_taters.mod_updater.util.ModSources

/**
 * Mods that have a direct link
 */
data class DirectMod(
    override val url: String,

    override val hashType: HashTypes? = null,

    override var hash: String? = null,
)
: ModBase(
    source = ModSources.DIRECT,
    fileName = url.substringAfterLast('/'),
), IHash
