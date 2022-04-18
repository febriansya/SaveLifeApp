package com.example.savelifeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.savelifeapp.databinding.ActivityMainFragmentBinding
import com.example.savelifeapp.login.LoginActivity
import com.example.savelifeapp.onBoarding.AdapterOnBoarding


private const val NUM_PAGES = 3

class MainFragmentActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainFragmentBinding

    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(_binding, position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainFragmentBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val pagerAdapter = AdapterOnBoarding(this, NUM_PAGES)
        _binding.viewPager2.adapter = pagerAdapter
        _binding.viewPager2.registerOnPageChangeCallback(onBoardingPageChangeCallback)


        _binding.btnContinue.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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

    private fun updateCircleMarker(binding:ActivityMainFragmentBinding,position:Int){
        when(position){
            0 ->{
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
            3 ->{
                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_red))
                binding.ivSecondCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
                binding.ivThirdCircle.setImageDrawable(getDrawable(R.drawable.view_slider_grey))
            }
        }
    }

}