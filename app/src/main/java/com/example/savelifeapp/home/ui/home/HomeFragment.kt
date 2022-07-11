package com.example.savelifeapp.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.databinding.FragmentHomeBinding
import com.example.savelifeapp.home.ui.home.viepager.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    lateinit var adapter: RecyclerAdapterStokDarah
    lateinit private var mRecylerView: RecyclerView
    private lateinit var sectionPagerAdapter: SectionPagerAdapter


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    forViewpager fragment bantu dan kegiatan
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecylerView = binding.RecylerViewItemStokDarah
        mRecylerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val dataset = arrayOfNulls<String>(12)
        for (i in dataset.indices) {
            dataset[i] = "$i"
        }
        adapter = RecyclerAdapterStokDarah(dataset)
        mRecylerView.adapter = adapter
//       implementation viewpager di dalam frgment di activity beda lagi
        sectionPagerAdapter = SectionPagerAdapter(this)
        with(binding){
            viewPager2.adapter  = sectionPagerAdapter
            TabLayoutMediator(tabLayout,viewPager2){tab,position ->
                when(position){
                    0 -> tab.text = "Kegiatan"
                    1 -> tab.text = "Bantu"
                }
            }.attach()
        }
    }


//    wajib ketika menggunakan fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}