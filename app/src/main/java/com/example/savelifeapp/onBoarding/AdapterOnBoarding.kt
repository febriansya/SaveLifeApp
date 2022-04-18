package com.example.savelifeapp.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterOnBoarding(activity: AppCompatActivity, private val itemCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemCount
    }
    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment.getInstance(position)
    }
}