package com.example.savelifeapp.ui.home.viepager.bantu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Bantu
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.databinding.FragmentViewPagerBantuBinding
import com.example.savelifeapp.ui.home.viepager.kegiatan.KegiatanAdapater
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewPagerBantuFragment : Fragment(),DetailBantuListener {

    private lateinit var rvBantu: RecyclerView
    private lateinit var list: ArrayList<Bantu>
//    private val list = ArrayList<Bantu>()
    lateinit var bantuAdapater: BantuAdapater
    private var _binding: FragmentViewPagerBantuBinding? = null
    private val binding get() = _binding!!
    val viewModel: BantuViewModel by viewModels()


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
        observe()
        showRecyclerList()
    }

//    private fun getData(): ArrayList<Bantu> {
//        val bantu = ArrayList<Bantu>()
//        val namaPasien = resources.getStringArray(R.array.nama_pasien)
//        val lokasiPasien = resources.getStringArray(R.array.lokasi_pasien)
//        val golDarah = resources.getStringArray(R.array.gol_darah)
//        val dataPhoto = resources.obtainTypedArray(R.array.poto_pasien)
//        for (i in namaPasien.indices) {
//            val hero = Bantu(
//                namaPasien[i],
//                lokasiPasien[i],
//                golDarah[i],
//                dataPhoto.getResourceId(i, -1)
//            )
//            bantu.add(hero)
//        }
//        return bantu
//    }

    private fun showRecyclerList() {
        rvBantu = view!!.findViewById(R.id.rv_bantu)
        rvBantu.setHasFixedSize(true)
        rvBantu.layoutManager = LinearLayoutManager(requireContext())
        list = arrayListOf()
        bantuAdapater =  BantuAdapater(list,this)
        rvBantu.adapter = bantuAdapater
        viewModel.getBantuPasien(list,bantuAdapater)
    }


    fun observe() {
        viewModel.bantu.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    Log.d("loading", "loading")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                }
            }
        }
    }
    override fun itemClicked(bantu: Bantu) {
        val intent = Intent(requireContext(),DetailBantuActivity::class.java)
        intent.putExtra("data",bantu)
        startActivity(intent)
      toast(bantu.namaPengirim)
    }

}