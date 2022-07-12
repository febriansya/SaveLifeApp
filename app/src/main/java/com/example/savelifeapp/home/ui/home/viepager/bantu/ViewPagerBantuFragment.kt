package com.example.savelifeapp.home.ui.home.viepager.bantu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Bantu
import com.example.savelifeapp.data.Kegiatan
import com.example.savelifeapp.home.ui.home.viepager.kegiatan.KegiatanAdapater

class ViewPagerBantuFragment : Fragment() {

    private lateinit var rvBantu: RecyclerView
    private val list = ArrayList<Bantu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_bantu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBantu = view.findViewById(R.id.rv_bantu)
        rvBantu.setHasFixedSize(true)

        list.addAll(listHeroes)
        showRecyclerList()

    }

    private val listHeroes: ArrayList<Bantu>
        get() {
            val namaPasien = resources.getStringArray(R.array.nama_pasien)
            val lokasiPasien = resources.getStringArray(R.array.lokasi_pasien)
            val golDarah = resources.getStringArray(R.array.gol_darah)
            val dataPhoto = resources.obtainTypedArray(R.array.poto_pasien)

            val listHero = ArrayList<Bantu>()
            for (i in namaPasien.indices) {
                val hero = Bantu(
                    namaPasien[i],
                    lokasiPasien[i],
                    golDarah[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecyclerList() {
        rvBantu.layoutManager = LinearLayoutManager(requireContext())
        val listHeroAdapter = BantuAdapater(list)
        rvBantu.adapter = listHeroAdapter
    }
}