package com.st10083210.hadeda.ui.BirdIndex

data class BirdIndexModel(
    val commonName: String,
    val scientificName: String,
    val location: String
) {
    companion object { var indexList : ArrayList<BirdIndexModel> = ArrayList() }
}
