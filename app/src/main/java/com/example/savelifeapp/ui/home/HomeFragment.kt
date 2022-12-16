package com.example.savelifeapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Stok
import com.example.savelifeapp.databinding.FragmentHomeBinding
import com.example.savelifeapp.ui.home.viepager.SectionPagerAdapter
import com.google.android.material.shape.CornerSize

import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private lateinit var stokArrayList: ArrayList<Stok>
    private lateinit var stokAdapter: StokAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var userCurrent: FirebaseAuth

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        userCurrent = FirebaseAuth.getInstance()

//        recylerview for showing stock blood
        binding.RecylerViewItemStokDarah.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
        binding.RecylerViewItemStokDarah.setHasFixedSize(true)
        stokArrayList = arrayListOf()

        stokAdapter = StokAdapter(stokArrayList)
        binding.RecylerViewItemStokDarah.adapter = stokAdapter

        EventChangedListener()
        getProfile()
//       implementation viewpager di dalam frgment dan  activity caranya berbeda
        sectionPagerAdapter = SectionPagerAdapter(this)
        with(binding) {
            viewPager2.adapter = sectionPagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.text = "Kegiatan"
                    1 -> tab.text = "Pasien"
                }
            }.attach()
        }
    }

    private fun EventChangedListener() {
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


    private fun getProfile() {

        db.collection("UserApp").document(userCurrent.currentUser?.uid.toString())
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.welcomeName.text = task.result.getString("nama")
                    Picasso.get().load(task.result.getString("image")).into(binding.profile)
                }
            }
    }

    //    wajib ketika menggunakan fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}