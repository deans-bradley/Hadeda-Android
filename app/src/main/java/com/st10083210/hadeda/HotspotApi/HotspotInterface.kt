package com.st10083210.hadeda.HotspotApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
//https://documenter.getpostman.com/view/664302/S1ENwy59#f4f59f90-854e-4ba6-8207-323a8cf0bfe0
//reference for finding bird hotspots
interface HotspotInterface {
    @GET("v2/data/obs/ZA/recent/notable")
    fun getObservations(
        @Header("x-ebirdapitoken") apiKey: String,
        @Query("detail") detail: String = "simple",
        @Query("maxResults") maxResults: String = "100"
    ): Call<List<RecentObservationModel>>

    @GET("v2/ref/hotspot/geo")
    fun getNearbyHotspots(
        @Header("x-ebirdapitoken") apiKey: String,
        @Query("lat") latitude: Double?,
        @Query("lng") longitude: Double?,
        //@Query("back") back: Int,
        @Query("dist") distance: Int,
        @Query("fmt") format: String
    ): Call<List<HotspotModel>>
}