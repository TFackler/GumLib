package gumroad_search.dto

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val count: Int,
    val average: Double,
)
