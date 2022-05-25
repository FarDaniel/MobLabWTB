package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val movieId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("year")
    val year: Int? = null,
    @SerializedName("imDbRating")
    val rating: Double? = null,
    @SerializedName("plot")
    val description: String? = null
)
