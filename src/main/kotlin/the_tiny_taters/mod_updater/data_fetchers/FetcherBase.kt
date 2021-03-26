package the_tiny_taters.mod_updater.data_fetchers

import the_tiny_taters.mod_updater.mod_data.ModBase

/**
 * The base class for all data fetchers
 */
abstract class FetcherBase {
    /**
     * Fills a mod with its properties, fetched from an API
     * 
     * @param mod the mod data object
     * @return whether or not all data was successfully fetched
     */
    open fun fetchProperties(mod: ModBase): Boolean {
        return setVersion(mod) // && fetchUrl(mod)
    }

    /**
     * Sets the version of the mod to the latest version if not already set
     *
     * @param mod the mod data object
     * @return whether or not a version was successfully set
     */
    protected abstract fun setVersion(mod: ModBase): Boolean
/*
    /**
     * Finds the download URL for a mod
     *
     * @param mod the mod data object
     * @return whether or not a url was successfully found
     */
    protected abstract fun fetchUrl(mod: ModBase): Boolean
*/
    /**
     * Finds the latest version and related data of the requested mod
     *
     * @param mod the mod data object
     * @return whether or not the latest version was successfully found
     */
    protected abstract fun fetchLatestVersion(mod: ModBase): Boolean
}