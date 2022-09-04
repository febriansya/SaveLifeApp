package com.example.savelifeapp.ui.home.viepager.kegiatan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.databinding.FragmentViewPagerKegiatanBinding
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class ViewPagerKegiatanFragment : Fragment() {
    private var _binding: FragmentViewPagerKegiatanBinding? = null
    private val binding get() = _binding!!


    private lateinit var rvKegiatan: RecyclerView
    private lateinit var kegiatanArrayList: ArrayList<Kegiatan>
    lateinit var kegiatanAdapater: KegiatanAdapater
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerKegiatanBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvKegiatan = view.findViewById(R.id.rv_kegiatan)
        rvKegiatan.layoutManager = LinearLayoutManager(requireContext())
        rvKegiatan.setHasFixedSize(true)
        kegiatanArrayList = arrayListOf()

        kegiatanAdapater = KegiatanAdapater(kegiatanArrayList)
        rvKegiatan.adapter = kegiatanAdapater
        EventChangedListener()

//        getData()
//        showRecyclerList()

    }

    private fun EventChangedListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Kegiatan")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            kegiatanArrayList.add(dc.document.toObject(Kegiatan::class.java))
                        }
                    }
                    kegiatanAdapater.notifyDataSetChanged()
                }
            })
    }

//    private fun getData(): ArrayList<Kegiatan> {
//        val bantuList = ArrayList<Kegiatan>()
//        val dataName = resources.getStringArray(R.array.nama_kegiatan)
//        val dataDescription = resources.getStringArray(R.array.lokasi_kegiatan)
//        val tanggal = resources.getStringArray(R.array.tanggal_kegiatan)
//        val jam = resources.getStringArray(R.array.jam_kegiatan)
//        val dataPhoto = resources.obtainTypedArray(R.array.poto_pmi)
//
//        for (i in dataName.indices) {
//            val hero = Kegiatan(
//                dataName[i],
//                dataDescription[i],
//                tanggal[i],
//                jam[i],
//                dataPhoto.getResourceId(i, -1)
//            )
//            bantuList.add(
//                hero
//            )
//        }
//        return bantuList
//    }

//    private fun showRecyclerList() {
//        rvKegiatan.layoutManager = LinearLayoutManager(requireContext())
//        val listHeroAdapter = KegiatanAdapater(getData())
//        rvKegiatan.adapter = listHeroAdapter
//    }
}
