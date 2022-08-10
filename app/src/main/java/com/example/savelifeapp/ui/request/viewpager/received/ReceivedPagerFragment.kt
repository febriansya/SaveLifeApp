package com.example.savelifeapp.ui.request.viewpager.received

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Received
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceivedPagerFragment : Fragment() {
    private lateinit var rvReceived: RecyclerView
    private val list = ArrayList<Received>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvReceived = view.findViewById(R.id.rv_received)
        rvReceived.setHasFixedSize(true)

        list.addAll(listReceived)
        showRecyclerList()

    }


    private val listReceived: ArrayList<Received>
        get() {
            val namaReceived = resources.getStringArray(R.array.nama_pasien)
            val golDarah = resources.getStringArray(R.array.gol_darah)
            val photo = resources.obtainTypedArray(R.array.poto_pasien)
            val lokasiPasien = resources.getStringArray(R.array.lokasi_pasien)


            val listReceived = ArrayList<Received>()
            for (i in namaReceived.indices) {
                val receive = Received(
                    namaReceived[i],
                    photo.getResourceId(i, -1),
                    lokasiPasien[i],
                    golDarah[i]
                )
                listReceived.add(receive)
            }
            return listReceived
        }


    private fun showRecyclerList() {
        rvReceived.layoutManager = LinearLayoutManager(requireContext())
        val listHeroAdapter = ReceivedAdapter(list)
        rvReceived.adapter = listHeroAdapter
    }

}