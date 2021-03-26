package the_tiny_taters.mod_updater.util

import java.security.MessageDigest

/**
 * Various hash types because different APIs use different hashes
 */
enum class HashTypes(val digest: MessageDigest, private val asString: String) {
    /**
     * The SHA1 hash type, used by Modrinth. They also use SHA512, but only recently uploaded mods use it, others only
     * have a SHA1 hash
     */
    SHA1(MessageDigest.getInstance("SHA-1"), "sha1"),

    /**
     * The SHA256 hash type
     */
    SHA256(MessageDigest.getInstance("SHA-256"), "sha256"),

    /**
     * The MD5 hash type
     */
    MD5(MessageDigest.getInstance("MD5"), "md5"),;

    override fun toString(): String {
        return asString
    }
}