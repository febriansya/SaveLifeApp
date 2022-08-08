package com.example.savelifeapp.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.savelifeapp.databinding.ActivitySplashScreenBinding
import com.example.savelifeapp.ui.onBoarding.MainFragmentActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySplashScreenBinding

    var timer: Long = 5000L
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, MainFragmentActivity::class.java)
            startActivity(intent)
            finish()
        }, timer)
    }
// semangat ya 🥰
}

