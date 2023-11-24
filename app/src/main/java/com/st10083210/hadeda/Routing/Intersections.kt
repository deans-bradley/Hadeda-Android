package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class Intersections(

    @SerializedName("classes"           ) var classes         : ArrayList<String>  = arrayListOf(),
    @SerializedName("entry"             ) var entry           : ArrayList<Boolean> = arrayListOf(),
    @SerializedName("bearings"          ) var bearings        : ArrayList<Int>     = arrayListOf(),
    @SerializedName("duration"          ) var duration        : Double?            = null,
    @SerializedName("mapbox_streets_v8" ) var mapboxStreetsV8 : MapboxStreetsV8?   = MapboxStreetsV8(),
    @SerializedName("is_urban"          ) var isUrban         : Boolean?           = null,
    @SerializedName("admin_index"       ) var adminIndex      : Int?               = null,
    @SerializedName("out"               ) var out             : Int?               = null,
    @SerializedName("weight"            ) var weight          : Double?            = null,
    @SerializedName("geometry_index"    ) var geometryIndex   : Int?               = null,
    @SerializedName("location"          ) var location        : ArrayList<Double>  = arrayListOf()
)
