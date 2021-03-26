package the_tiny_taters.mod_updater.data_fetchers

import net.minecraft.SharedConstants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import the_tiny_taters.mod_updater.mod_data.ModBase
import the_tiny_taters.mod_updater.mod_data.ModrinthMod
import the_tiny_taters.mod_updater.mod_data.IHash
import the_tiny_taters.mod_updater.Common.mcVersion

/**
 * Gets information from the Modrinth API to fill up properties in a mod data object
 */
object ModrinthDataFetcher : FetcherBase() {
    override fun fetchProperties(mod: ModBase): Boolean {
        return fetchProjectId(mod as ModrinthMod) && super.fetchProperties(mod)
    }

    override fun setVersion(mod: ModBase): Boolean {
        check(mod is ModrinthMod)
        return if (mod.version == null) {
            fetchLatestVersion(mod)
        } else {
            verifyVersion(mod)
            getVersionData(mod)
        }
    }

    override fun fetchLatestVersion(mod: ModBase): Boolean {
        check(mod is ModrinthMod)
        val data = khttp.get(
            "https://api.modrinth.com/api/v1/mod/${mod.projectId}/version" +
                    "?game_versions=[\"$mcVersion\"]&loaders=[\"fabric\"]"
        ).jsonArray

        for (entry in data) {
            check(entry is JSONObject)
            mod.version = entry["version_number"].toString()
            mod.versionId = entry["id"].toString()
            getVersionData(mod)
            return true
        }

        return false
    }

    /**
     * Converts a slug to a project id
     *
     * @param mod the mod data object
     * @return whether or not the project id was found
     */
    private fun fetchProjectId(mod: ModrinthMod): Boolean {
        try {
            val data = khttp.get("https://api.modrinth.com/api/v1/mod/${mod.slug}").jsonObject
            mod.projectId = data["id"].toString()
            mod.name = data["title"].toString()
            mod.slug = data["slug"].toString()
        } catch (e: JSONException) {
            return false
        }

        return mod.projectId != null
    }

    /**
     * Checks if the specified mod version exists, and sets the [version id][ModrinthMod.versionId] if it does
     *
     * @param mod the mod data object
     * @return whether or not the version exists
     */
    private fun verifyVersion(mod: ModrinthMod): Boolean {
        try {
            val data = khttp.get(
                "https://api.modrinth.com/api/v1/mod/${mod.projectId}/version" +
                        "?game_versions=[\"$mcVersion\"]&loaders=[\"fabric\"]"
            ).jsonArray

            for (entry in data) {
                check(entry is JSONObject)
                if (mod.version!! == entry["version_number"].toString()) {
                    mod.versionId = entry["id"].toString()
                    return true
                }
            }

            return false
        } catch (e: JSONException) {
            return false
        } catch (e: ClassCastException) {
            return false
        }
    }

    /**
     * Gets information about the version of the mod object. The [file name][ModBase.fileName], [hash][IHash.hash], and
     * [url][ModBase.url] are set
     *
     * @param mod the mod data object
     * @return whether or not information was successfully retrieved
     */
    private fun getVersionData(mod: ModrinthMod): Boolean {
        val data = khttp.get(
            "https://api.modrinth.com/api/v1/version/${mod.versionId}"
        ).jsonObject

        return try {
            mod.fileName = ((data["files"] as JSONArray)[0] as JSONObject)["filename"].toString()
            mod.hash =
                (((data["files"] as JSONArray)[0] as JSONObject)["hashes"] as JSONObject)["sha1"]
                    .toString()
                    .toUpperCase()
            mod.url = "https://api.modrinth.com/api/v1/version_file/${mod.hash.toString().toLowerCase()}/download"

            true
        } catch (e: JSONException) {
            false
        } catch (e: ClassCastException) {
            false
        }
    }
}
