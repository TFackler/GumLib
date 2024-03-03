package model

data class Group (
    val id: Long,
    val name: String,
    val description: String,
    val assets: List<Asset>,
)