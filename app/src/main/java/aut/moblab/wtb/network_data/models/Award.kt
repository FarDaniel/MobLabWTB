package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class Award(
    @SerializedName("outcomeTitle")
    val outcome: String,
    @SerializedName("outcomeCategory")
    val category: String,
    @SerializedName("outcomeDetails")
    val details: List<AwardDetails>
)
