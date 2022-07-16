package com.example.savelifeapp.home.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Kegiatan
import com.example.savelifeapp.data.Stok
import com.example.savelifeapp.databinding.FragmentHomeBinding

import com.example.savelifeapp.home.ui.home.viepager.SectionPagerAdapter
import com.example.savelifeapp.home.ui.home.viepager.kegiatan.KegiatanAdapater
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    lateinit private var mRecylerView: RecyclerView
    private lateinit var sectionPagerAdapter: SectionPagerAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private val list = ArrayList<Stok>()

    //    forViewpager fragment bantu dan kegiatan
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recylerview for showing stock blood
        mRecylerView = view.findViewById(R.id.RecylerView_item_stok_darah)
        mRecylerView.setHasFixedSize(true)

//        ini jangan lupa pake adall kareana data berbentuk array

//        list.addAll(listStok)
        addData()
        showRecyclerList()

//       implementation viewpager di dalam frgment di activity caranya berbeda
        sectionPagerAdapter = SectionPagerAdapter(this)
        with(binding) {
            viewPager2.adapter = sectionPagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.text = "Kegiatan"
                    1 -> tab.text = "Bantu"
                }
            }.attach()
        }
    }

//    private val listStok: ArrayList<Stok>
//        get() {
//            val stok = resources.getStringArray(R.array.stok)
//            val jenis = resources.getStringArray(R.array.jenis_darah)
//            val listHero = ArrayList<Stok>()
//            for (i in stok.indices) {
//                val stok = Stok(
//                    stok[i],
//                    jenis[i],
//                )
//                listHero.add(stok)
//            }
//            return listHero
//        }


    private fun addData(): ArrayList<Stok> {

        val stoks = ArrayList<Stok>()

        val stok = resources.getStringArray(R.array.stok)
        val jenis = resources.getStringArray(R.array.jenis_darah)

        for (i in stok.indices) {
            val stok = Stok(
                stok[i],
                jenis[i]
            )
            stoks.add(stok)
        }
        return stoks
    }

    private fun showRecyclerList() {
        mRecylerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
        val listHeroAdapter = StokAdapter(addData())
        mRecylerView.adapter = listHeroAdapter
    }

//    memasukan data dari string ke variabel berbentuk array


    //    wajib ketika menggunakan fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}