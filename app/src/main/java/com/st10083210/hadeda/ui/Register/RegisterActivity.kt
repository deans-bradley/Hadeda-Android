package com.st10083210.hadeda.ui.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.st10083210.hadeda.MainActivity
import com.st10083210.hadeda.R
import com.st10083210.hadeda.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    //https://firebase.google.com/docs/android/setup
    //firebase docs for implementing firebase
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater) // Initialize the binding object
        setContentView(binding.root)

        lateinit var auth: FirebaseAuth
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener()
        {
            val isEqual = binding.passwordEt.text.toString().equals(binding.conPasswordEt.text.toString());
            if (isEqual)
            {
                val username = binding.emailEt.text.toString()
                val password = binding.passwordEt.text.toString()
                auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this)
                {
                        task->
                    if(task.isSuccessful)
                    {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Signed in", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this, "Please ensure that you have entered your details " +
                                "correctly", Toast.LENGTH_LONG).show()
                    }

                }
                    .addOnFailureListener(this)
                    {
                        task->
                        Toast.makeText(this,"Password is not valid", Toast.LENGTH_LONG).show()
                    }

            }
            else
            {
                Toast.makeText(this, "Passwords Do not match", Toast.LENGTH_LONG).show()
            }
        }
    }
}