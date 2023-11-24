package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class RouteCustomResponse (
    @SerializedName("routes") val routes: List<CustomRoute>
        )

data class CustomRoute (
    @SerializedName("geometry") val geometry: CustomGeometry
)

data class CustomGeometry (
    @SerializedName("coordinates") val coordinates: List<List<Double>>,
    @SerializedName("type") val type: String
)