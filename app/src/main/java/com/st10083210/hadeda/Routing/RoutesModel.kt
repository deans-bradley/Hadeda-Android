package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class RoutesModel (

  @SerializedName("geometry"    ) var geometry   : Geometry?,
  @SerializedName("legs"        ) var legs       : ArrayList<LegsModel> = arrayListOf(),
  @SerializedName("weight_name" ) var weightName : String?         = null,
  @SerializedName("weight"      ) var weight     : Double?         = null,
  @SerializedName("duration"    ) var duration   : Double?         = null,
  @SerializedName("distance"    ) var distance   : Double?         = null

)