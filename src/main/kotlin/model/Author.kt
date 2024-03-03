package model

import java.time.Instant

data class Author(
    val slug: String,
    val name: String,
    val url: String,
    // TODO: main/2024-03-03 change this to be a displayable picture type
    val profilePicture: String,
    val updatedLastTimeStamp: Instant,
)
