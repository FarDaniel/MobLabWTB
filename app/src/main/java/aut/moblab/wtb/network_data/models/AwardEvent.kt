package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class AwardEvent(
    @SerializedName("eventYear")
    val year: Int,
    @SerializedName("outcomeItems")
    val details: List<Award>
)
