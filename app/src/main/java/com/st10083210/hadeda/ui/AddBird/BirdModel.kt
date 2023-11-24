package com.st10083210.hadeda.ui.AddBird

data class BirdModel(
    var sBirdName:String,
    var sFamily:String,
    var sSex:String,
    var sLocation: String
    //var iHabitsImages: Uri?
){
    companion object{ var birdLst : ArrayList<BirdModel> = ArrayList()}
}
