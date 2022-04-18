package com.example.savelifeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.databinding.ActivityMainFragmentBinding

class MainFragmentActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainFragmentBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnContinue.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}