package com.st10083210.hadeda.Routing

import com.google.gson.annotations.SerializedName

data class Admins(

    @SerializedName("iso_3166_1_alpha3" ) var iso3166Alpha3 : String? = null,
    @SerializedName("iso_3166_1" ) var iso3166 : String? = null

)