package com.st10083210.hadeda.ui.Splash

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.st10083210.hadeda.HotspotApi.HotspotInterface
import com.st10083210.hadeda.HotspotApi.RecentObservationModel
import com.st10083210.hadeda.MainActivity
import com.st10083210.hadeda.R
import com.st10083210.hadeda.ui.BirdIndex.BirdIndexModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : AppCompatActivity() {

    private var obsList: ArrayList<BirdIndexModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES

        if (isNightMode) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.indigo_dye)
        } else {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.argentinian_blue)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            getRecentObservations()

            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }

    private fun getRecentObservations() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ebird.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val birdService = retrofit.create(HotspotInterface::class.java)

        // GET /v2/data/obs/ZA/recent/notable
        val apiKey = "cgg84aonm2ti"

        val call = birdService.getObservations(apiKey)
        call.enqueue(object: Callback<List<RecentObservationModel>> {
            override fun onResponse(
                call: Call<List<RecentObservationModel>>,
                response: Response<List<RecentObservationModel>>
            ) {
                if (response.isSuccessful) {
                    Log.e("Request Success", "Status code: ${response.code()}")

                    val observation = response.body()

                    if (observation != null) {
                        Log.e("Request Success", "-->$observation")
                        for (point in observation) {
                            obsList.add(BirdIndexModel(point.comName.toString(), point.sciName.toString(), point.locName.toString()))
                        }

                        BirdIndexModel.indexList = obsList
                    }
                    else {
                        Log.e("Request Error", "Response body is null")
                    }
                }
                else {
                    Log.e("Request Error", "API request failed with status code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<RecentObservationModel>>, t: Throwable) {
                Log.e("Request Error", "API request failed with exception: $t")
            }
        })
    }
}