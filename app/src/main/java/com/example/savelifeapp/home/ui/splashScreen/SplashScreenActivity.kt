package com.example.savelifeapp.home.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.savelifeapp.databinding.ActivitySplashScreenBinding
import com.example.savelifeapp.home.ui.onBoarding.MainFragmentActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySplashScreenBinding

    var timer: Long = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
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
}