package aut.moblab.wtb.local_data.models

data class MovieTags(
    val id: String,
    val movieId: String,
    val title: String,
    val isFavourite: Boolean,
    val isBlackListed: Boolean
)