package com.st10083210.hadeda.ui.AddBird

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.st10083210.hadeda.MainActivity
import com.st10083210.hadeda.R
import com.st10083210.hadeda.databinding.ActivityAddBirdBinding

private lateinit var name: String
private lateinit var family: String
private lateinit var sex: String
private lateinit var location: String

class AddBirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bird)

        binding = ActivityAddBirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.saveBtn.setOnClickListener {
            name = binding.nameEt.text.toString()
            family = binding.familyEt.text.toString()
            sex = binding.sexEt.text.toString()
            location = binding.locationEt.text.toString()

            if (name.isEmpty() || family.isEmpty() || sex.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            } else {
                val bird = BirdModel(name, family, sex, location)
                BirdModel.birdLst.add(bird)

                Toast.makeText(this, "Bird added!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}