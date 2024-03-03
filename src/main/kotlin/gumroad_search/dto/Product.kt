package gumroad_search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    /**
     * // TODO: main/2024-03-03 add description
     */
    val id: String,
    /**
     * // TODO: main/2024-03-03 add description
     */
    val permalink: String,
    /**
     * Display name of the product.
     */
    val name: String,
    val creator: Creator,
    val ratings: Rating,
    /**
     * Price in the smallest unit of the currency. E.g. cents for USD.
     */
    val price: Int,
    @SerialName("currency_code")
    val currencyCode: String,
    /**
     * Url of the thumbnail displayed in the grid view of the search results.
     *
     * If the creator has not set a thumbnail, then the first image of the covers is rescaled and used.
     */
    @SerialName("thumbnail_url")
    val thumbnailUrl: String?,
    /**
     * Url of the product page.
     */
    val url: String,
)