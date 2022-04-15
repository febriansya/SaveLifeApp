package com.example.savelifeapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}