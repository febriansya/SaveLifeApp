package com.example.savelifeapp.home.ui.request.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.savelifeapp.home.ui.request.viewpager.mrequest.RequestPagerFragment
import com.example.savelifeapp.home.ui.request.viewpager.received.ReceivedPagerFragment

class SectionPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = RequestPagerFragment()
            1 -> fragment = ReceivedPagerFragment()

        }
        return fragment
    }
}