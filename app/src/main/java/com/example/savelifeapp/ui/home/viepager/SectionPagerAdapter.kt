package com.example.savelifeapp.ui.home.viepager


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.savelifeapp.ui.home.viepager.bantu.ViewPagerBantuFragment
import com.example.savelifeapp.ui.home.viepager.kegiatan.ViewPagerKegiatanFragment


// constructor fm akan digunakan ketika viewpager diterpkan di fragment bukan di activity, menggunakn FragmentStateAdapter
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