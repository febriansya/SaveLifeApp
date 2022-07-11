package com.example.savelifeapp.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
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
    companion object {
        @StringRes
        private var TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        mRecylerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        val dataset = arrayOfNulls<String>(12)
//        for (i in dataset.indices) {
//            dataset[i] = "$i"
//        }
//        adapter = RecyclerAdapterStokDarah(dataset)
//        mRecylerView.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}