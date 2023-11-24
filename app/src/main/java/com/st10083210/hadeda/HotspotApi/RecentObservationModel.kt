package com.st10083210.hadeda.HotspotApi

import com.google.gson.annotations.SerializedName

data class RecentObservationModel(
    @SerializedName("comName") val comName: String?,
    @SerializedName("sciName") val sciName: String?,
    @SerializedName("locName") val locName: String?
)
