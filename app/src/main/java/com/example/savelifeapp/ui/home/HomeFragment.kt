package com.example.savelifeapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Stok
import com.example.savelifeapp.databinding.FragmentHomeBinding
import com.example.savelifeapp.ui.home.viepager.SectionPagerAdapter

import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit private var mRecylerView: RecyclerView
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private lateinit var stokArrayList: ArrayList<Stok>
    private lateinit var stokAdapter: StokAdapter
    private lateinit var db: FirebaseFirestore


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recylerview for showing stock blood
        mRecylerView = view.findViewById(R.id.RecylerView_item_stok_darah)
        mRecylerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
        mRecylerView.setHasFixedSize(true)
        stokArrayList = arrayListOf()

        stokAdapter = StokAdapter(stokArrayList)
        mRecylerView.adapter = stokAdapter

        EventChangedListener()
//       implementation viewpager di dalam frgment dan  activity caranya berbeda
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

    private fun EventChangedListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Stok")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("firesetore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            stokArrayList.add(dc.document.toObject(Stok::class.java))
                        }
                    }
                    stokAdapter.notifyDataSetChanged()
                }
            })
    }


    private fun getProfile(){

    }
    //    wajib ketika menggunakan fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}