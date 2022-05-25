package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class MovieItems(
    @SerializedName("items")
    val movies: List<Movie>
)