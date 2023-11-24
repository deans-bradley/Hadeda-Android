package com.st10083210.hadeda.ui.Settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.st10083210.hadeda.MainActivity
import com.st10083210.hadeda.MyApplication
import com.st10083210.hadeda.R
import com.st10083210.hadeda.UserPreferences
import com.st10083210.hadeda.UserPreferencesRepository
import com.st10083210.hadeda.databinding.ActivitySettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.prefs.Preferences


class SettingsActivity : AppCompatActivity() {

    private val app: MyApplication by lazy {
        application as MyApplication
    }

    private val userPreferencesRepository: UserPreferencesRepository by lazy {
        app.userPreferences
    }

    private lateinit var binding: ActivitySettingsBinding

    private var distRadius: Int = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // SeekBar progress change
                binding.distTv.text = binding.seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // SeekBar progress start
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // SeekBar progress stop
                binding.distTv.text = binding.seekBar.progress.toString()
            }
        })

        binding.backBtn.setOnClickListener {

            lifecycleScope.launch {

                // Update user preference
                distRadius = binding.seekBar.progress
                app.userPreferences.updateDistRadius(distRadius)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {

            // Get user preference
            distRadius = app.userPreferences.userPreferencesFlow.first().distRadius

            binding.distTv.text = distRadius.toString()
            binding.seekBar.progress = distRadius
        }
    }
}