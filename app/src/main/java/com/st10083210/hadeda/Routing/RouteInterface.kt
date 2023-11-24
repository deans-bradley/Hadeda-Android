package com.st10083210.hadeda.Routing

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RouteInterface {

    @GET("directions/v5/mapbox/driving/{coordinates}")
    fun getRoute(
        @Path(value = "coordinates", encoded = true) coordinates: String,
        @Query("alternatives") alternatives: Boolean,
        @Query("language") language: String,
        @Query("geometries") geometries: String,
        @Query("overview") overview: String,
        @Query("steps") steps: Boolean,
        @Query("access_token") access_token: String
    ): Call<RouteResponseModel>
}