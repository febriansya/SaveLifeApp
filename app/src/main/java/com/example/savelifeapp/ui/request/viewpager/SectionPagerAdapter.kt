package com.example.savelifeapp.ui.request.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.savelifeapp.ui.request.viewpager.mrequest.RequestPagerFragment
import com.example.savelifeapp.ui.request.viewpager.received.ReceivedPagerFragment

class SectionPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = ReceivedPagerFragment()
            1 -> fragment = RequestPagerFragment()
        }
        return fragment
    }
}