package gumroad_search.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    /**
     * Total number of products that match the given search terms.
     */
    val total: Int,
    val products: List<Product>,
)