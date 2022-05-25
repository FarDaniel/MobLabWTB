package aut.moblab.wtb.network_data.models

import com.google.gson.annotations.SerializedName

data class AwardDetails(
    @SerializedName("plainText")
    val description: String
)