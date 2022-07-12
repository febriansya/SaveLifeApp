package com.example.savelifeapp.home.ui.home.viepager.kegiatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Kegiatan
import com.example.savelifeapp.databinding.FragmentViewPagerKegiatanBinding

class ViewPagerKegiatanFragment : Fragment() {
    private var _binding: FragmentViewPagerKegiatanBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvKegiatan: RecyclerView
    private val list = ArrayList<Kegiatan>()


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
        rvKegiatan.setHasFixedSize(true)

        list.addAll(listHeroes)
        showRecyclerList()

    }

    private val listHeroes: ArrayList<Kegiatan>
        get() {
            val dataName = resources.getStringArray(R.array.nama_kegiatan)
            val dataDescription = resources.getStringArray(R.array.lokasi_kegiatan)
            val tanggal = resources.getStringArray(R.array.tanggal_kegiatan)
            val jam = resources.getStringArray(R.array.jam_kegiatan)
            val dataPhoto = resources.obtainTypedArray(R.array.poto_pmi)

            val listHero = ArrayList<Kegiatan>()
            for (i in dataName.indices) {
                val hero = Kegiatan(
                    dataName[i],
                    dataDescription[i],
                    tanggal[i],
                    jam[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecyclerList() {
        rvKegiatan.layoutManager = LinearLayoutManager(requireContext())
        val listHeroAdapter = KegiatanAdapater(list)
        rvKegiatan.adapter = listHeroAdapter
    }
}
