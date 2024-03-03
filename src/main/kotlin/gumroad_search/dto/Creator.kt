package gumroad_search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Creator(
    /**
     * Display name of the creator.
     */
    val name: String,
    /**
     * Url of the creator's profile.
     */
    @SerialName("profile_url")
    val profileUrl: String,
    /**
     * Url of the creator's profile picture.
     */
    @SerialName("avatar_url")
    val avatarUrl: String,
)
