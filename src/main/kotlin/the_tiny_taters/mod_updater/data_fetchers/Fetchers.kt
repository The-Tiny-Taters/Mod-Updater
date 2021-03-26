package the_tiny_taters.mod_updater.data_fetchers

/**
 * Gets data from the Modrinth, GitHub, and CurseForge APIs
 */
enum class Fetchers(val fetcher: FetcherBase) {
    /**
     * Holds all the methods for querying the Modrinth API
     */
    MODRINTH(ModrinthDataFetcher),

    /**
     * Holds all the methods for querying the GitHub API
     */
    GITHUB(GitHubDataFetcher),
}