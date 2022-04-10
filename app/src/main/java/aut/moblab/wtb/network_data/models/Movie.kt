package aut.moblab.wtb.network_data.models

data class Movie(
    val id: String,
    val movieId: String,
    val title: String,
    val image: String,
    val year: Int,
    val rating: Long,
    val description: String
)
