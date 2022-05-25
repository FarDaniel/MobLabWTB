package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class AwardItems(
    @SerializedName("items")
    val awards: List<AwardEvent>,
    @SerializedName("description")
    val description: String
)
