package com.example.savelifeapp.ui.home.viepager.kegiatan

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
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.databinding.FragmentViewPagerKegiatanBinding
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerKegiatanFragment : Fragment() {
    private var _binding: FragmentViewPagerKegiatanBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvKegiatan: RecyclerView
    private lateinit var list: ArrayList<Kegiatan>

    lateinit var kegiatanAdapater: KegiatanAdapater
    private lateinit var db: FirebaseFirestore
    val viewModel: KegiatanViewModel by viewModels()


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
        rvKegiatan.layoutManager = LinearLayoutManager(requireContext())
        list = arrayListOf()

        kegiatanAdapater = KegiatanAdapater(list)
        rvKegiatan.adapter = kegiatanAdapater
        observe()
        viewModel.getDataKegiatan(list, kegiatanAdapater)
    }

    fun observe() {
        viewModel.kegiatan.observe(viewLifecycleOwner) { state ->
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
}

