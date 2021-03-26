package the_tiny_taters.mod_updater.data_fetchers

import the_tiny_taters.mod_updater.mod_data.ModBase

/**
 * Gets information from the GitHub API to fill up properties in a mod data object
 */
object GitHubDataFetcher : FetcherBase() {
    override fun setVersion(mod: ModBase): Boolean {
        TODO("Not yet implemented")
    }

    override fun fetchLatestVersion(mod: ModBase): Boolean {
        TODO("Not yet implemented")
    }
}