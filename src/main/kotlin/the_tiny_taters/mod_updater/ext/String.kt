package the_tiny_taters.mod_updater.ext

import the_tiny_taters.mod_updater.util.HashTypes

/**
 * Converts string to its corresponding [HashType][HashTypes]
 */
fun String.toHashAlgorithm(): HashTypes? {
    return when (this.toUpperCase()) {
        "SHA1" -> HashTypes.SHA1
        "SHA256" -> HashTypes.SHA256
        "MD5" -> HashTypes.MD5
        else -> null
    }
}





