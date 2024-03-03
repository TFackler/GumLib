package gumroad_search

import gumroad_search.dto.Product
import gumroad_search.dto.SearchResult
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Thread.sleep

/**
 * Allows searching on Gumroad.
 */
class SearchManager {
    companion object {
        private const val DEFAULT_DELAY_BETWEEN_REQUESTS_MS = 500L

        private val BASE_URL = "https://discover.gumroad.com/".toHttpUrlOrNull() ?: error("Invalid URL")

        /**
         * Example: https://discover.gumroad.com/products/search?query=vrchat%20furry&from=10
         */
        private val API_SEARCH_BASE_URL =
            "https://discover.gumroad.com/products/search".toHttpUrlOrNull() ?: error("Invalid URL")

        private val partialJson = Json {
            ignoreUnknownKeys = true
        }
    }

    private val client = OkHttpClient()

    // TODO: main/2024-03-03 make this suspend
    /**
     * Makes a search request to Gumroad's API and returns a list of products.
     *
     * Note that Gumroad's API uses dynamic pagination,
     * so this method will make multiple requests if the [maxNumOfItems] if larger than a single page.
     *
     * @param searchTerms list of search terms.
     * @param maxNumOfItems maximum number of products to fetch.
     * @param delayBetweenRequests delay between requests in milliseconds.
     */
    fun search(
        searchTerms: List<String>,
        maxNumOfItems: Int,
        delayBetweenRequests: Long = DEFAULT_DELAY_BETWEEN_REQUESTS_MS
    ): List<Product> {
        val products = mutableListOf<Product>()
        var numberOfProductsToFetch = maxNumOfItems

        while (products.size < numberOfProductsToFetch) {
            val response = client
                .newCall(buildSearchRequestUrl(searchTerms, from = products.size + 1))
                .execute()

            val responseBody = response.body?.string()
            check(responseBody != null) { "Response body is null" }

            val searchResult = partialJson.decodeFromString<SearchResult>(responseBody)

            if (products.isEmpty()) {
                // first request
                if (searchResult.total < maxNumOfItems) {
                    numberOfProductsToFetch = searchResult.total
                }
            }
            if (products.size + searchResult.products.size > maxNumOfItems) {
                // adding all products found would exceed the limit
                products.addAll(searchResult.products.subList(0, maxNumOfItems - products.size))
            } else {
                products.addAll(searchResult.products)
            }
            sleep(delayBetweenRequests)
        }

        return products
    }

    /**
     * Builds a search request that will interact with Gumroad's API.
     *
     * @param searchTerms list of search terms.
     * @param from Gumroad uses dynamic pagination, so this parameter is used to specify the index of
     * the first product to fetch.
     */
    private fun buildSearchRequestUrl(searchTerms: List<String>, from: Int): Request {
        val url = API_SEARCH_BASE_URL.newBuilder()
            .addQueryParameter("query", searchTerms.joinToString(" "))
            .addQueryParameter("from", from.toString())
            .build()
        return Request.Builder().url(url).build()
    }

    /**
     * The human-readable counterpart of [buildSearchRequestUrl].
     */
    private fun buildWebsiteSearchRequestUrl(searchTerms: List<String>, from: Int): Request {
        val url = BASE_URL.newBuilder()
            .addQueryParameter("query", searchTerms.joinToString("+"))
            .addQueryParameter("from", from.toString())
            .build()
        return Request.Builder().url(url).build()
    }
}

fun main() {
    val searchManager = SearchManager()
    val products = searchManager.search(listOf("vrchat", "animal ears"), 15)
    println(products)
}