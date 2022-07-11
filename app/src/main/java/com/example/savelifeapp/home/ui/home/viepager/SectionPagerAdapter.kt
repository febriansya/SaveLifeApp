package com.example.savelifeapp.home.ui.home.viepager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = ViewPagerKegiatanFragment()
            1 -> fragment = ViewPagerBantuFragment()
        }
        return fragment
    }
}