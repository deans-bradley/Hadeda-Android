package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("type") var type: String? = null,
    @SerializedName("coordinates")var coordinates: ArrayList<ArrayList<Double>> = arrayListOf()
)
