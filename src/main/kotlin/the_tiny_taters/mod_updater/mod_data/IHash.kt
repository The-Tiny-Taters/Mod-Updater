package the_tiny_taters.mod_updater.mod_data

import the_tiny_taters.mod_updater.util.HashTypes

interface IHash {
    /**
     * Hash of the mod, according to API it is downloaded from
     */
    var hash: String?

    /**
     * The hash type
     */
    val hashType: HashTypes?
}