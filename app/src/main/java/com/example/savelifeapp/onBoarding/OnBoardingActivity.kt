package com.example.savelifeapp.onBoarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.savelifeapp.MainActivity
import com.example.savelifeapp.R


class OnBoardingActivity : AppCompatActivity() {

    //    inisialisaision shared preferences for set onboarding on time
    private lateinit var preference: SharedPreferences
    private val prefShowPreferences = "intro"
    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

//
//
//        preference = getSharedPreferences("introSlider", Context.MODE_PRIVATE)
//        btnNext = findViewById(R.id.btn_skip)
//
////        condision when app open firt time to show on boarding, when preference false intent to mainActicty
//        if (!preference.getBoolean(prefShowPreferences, true)) {
//            val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
//            startActivity(intent)
////            app cant back to onboarding,
//            finish()
//        }
//        btnNext.setOnClickListener {
//            val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//            val editor = preference.edit()
//            editor.putBoolean(prefShowPreferences, false)
//            editor.apply()
//        }
//    }
    }
}