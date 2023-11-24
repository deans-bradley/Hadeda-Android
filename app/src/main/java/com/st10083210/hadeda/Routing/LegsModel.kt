package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class LegsModel (

  @SerializedName("via_waypoints"    ) var viaWaypoints    : ArrayList<String> = arrayListOf(),
  @SerializedName("Admins"    ) var Admins    : ArrayList<Admins> = arrayListOf(),
  @SerializedName("steps"    ) var steps    : ArrayList<StepsModel> = arrayListOf(),
  @SerializedName("summary"  ) var summary  : String?           = null,
  @SerializedName("weight"   ) var weight   : Double?           = null,
  @SerializedName("duration" ) var duration : Double?           = null,
  @SerializedName("distance" ) var distance : Double?           = null

)