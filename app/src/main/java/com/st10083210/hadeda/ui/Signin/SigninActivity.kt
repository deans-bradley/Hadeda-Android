package com.st10083210.hadeda.ui.Signin

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
import com.st10083210.hadeda.databinding.ActivitySigninBinding
import com.st10083210.hadeda.ui.Register.RegisterActivity

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        binding = ActivitySigninBinding.inflate(layoutInflater) // Initialize the binding object
        setContentView(binding.root)

        //intitialize and get instance of fb
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener()  {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
            {
                    task->

                if(task.isSuccessful)
                {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
                }
            }
                .addOnFailureListener(this)
                {
                        task->

                    Toast.makeText(this,"Invalid username or password", Toast.LENGTH_LONG).show()
                }
        }

        binding.regBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}