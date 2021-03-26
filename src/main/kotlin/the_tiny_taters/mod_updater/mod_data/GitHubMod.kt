package the_tiny_taters.mod_updater.mod_data

import the_tiny_taters.mod_updater.util.ModSources

/**
 * Mod that is hosted on GitHub releases
 */
data class GitHubMod(
    /**
     * The author of the mod
     *
     * https://github.com/Author
     */
    val author: String,

    /**
     * The repository for the mod
     *
     * https://github.com/[Author][author]/Repository
     */
    val repo: String,

    override var url: String? = null,

    override var version: String? = null,
)
: ModBase(ModSources.GITHUB), IVersion
