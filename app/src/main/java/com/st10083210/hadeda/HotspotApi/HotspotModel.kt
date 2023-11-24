package com.st10083210.hadeda.HotspotApi

import com.google.gson.annotations.SerializedName

data class HotspotModel (
    @SerializedName("locId") val locId: String?,
    @SerializedName("locName") val name: String?, // Change to "locName"
    @SerializedName("lat") val latitude: Double?, // Change to "lat"
    @SerializedName("lng") val longitude: Double?, // Change to "lng"
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("subnationalCode") val subnationallCode: String?,
    @SerializedName("latestObsDt") val latestobsDt: String?, // Latest observation
    @SerializedName("numSpeciesAllTime") val numSpeciesAllTime: Int? // Number of species
)