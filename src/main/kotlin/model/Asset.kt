package model

import java.time.Instant

data class Asset(
    val slug: String,
    val name: String,
    val price: Double,
    val overallRating: Double,
    val numberOfRatings: Int,
    // TODO: main/2024-03-03 change this to be a displayable picture type
    val thumbnail: String,
    val url: String,
    val author: Author,
    val tags: List<Tag>,
    val updatedLastTimeStamp: Instant,
)


