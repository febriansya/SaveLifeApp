package com.example.savelifeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.savelifeapp.databinding.ActivityMainFragmentBinding
import com.example.savelifeapp.login.LoginActivity
import com.example.savelifeapp.onBoarding.AdapterOnBoarding


private const val NUM_PAGES = 3

class MainFragmentActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainFragmentBinding
    lateinit var preference: SharedPreferences
    val pref_show_intro = "intro"


    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(_binding, position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainFragmentBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        preference = getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        val pagerAdapter = AdapterOnBoarding(this, NUM_PAGES)
        _binding.viewPager2.adapter = pagerAdapter
        _binding.viewPager2.registerOnPageChangeCallback(onBoardingPageChangeCallback)

        _binding.btnContinue.setOnClickListener {
            if (_binding.viewPager2.currentItem == 2) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                val editor = preference.edit()
                editor.putBoolean(pref_show_intro, false)
                editor.apply()
            } else {
                moveWithContinue()
            }
        }
        skip()
    }

    override fun onDestroy() {
        _binding.viewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (_binding.viewPager2.currentItem == 0) {
            super.onBackPressed()
        } else {
            _binding.viewPager2.currentItem = _binding.viewPager2.currentItem - 1
        }
        super.onBackPressed()
    }

    private fun updateCircleMarker(binding: ActivityMainFragmentBinding, position: Int) {
        when (position) {
            0 -> {
                binding.ivFirstCircle.setImageDrawable(getDrawable(R.drawable.view_slider_red))
                binding.ivSecondCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
            }
            1 -> {
                binding.ivSecondCircle.setImageDrawable(getDrawable(R.drawable.view_slider_red))
                binding.ivFirstCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
            }
            2 -> {
                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_red))
                binding.ivSecondCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
                binding.ivFirstCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
            }
//            3 -> {
//                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_red))
//                binding.ivSecondCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
//                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
//            }
        }
    }

    fun moveWithContinue() {
        _binding.viewPager2.setCurrentItem(_binding.viewPager2.currentItem + 1, true)
    }

    fun skip() {
        val btn = _binding.tvButtonSkip
        btn.setOnClickListener {
            val intent = Intent(this@MainFragmentActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun onTimeOnBoarding() {

    }
}