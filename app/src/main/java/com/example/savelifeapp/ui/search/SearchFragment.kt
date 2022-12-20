package com.example.savelifeapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Bantu
import com.example.savelifeapp.databinding.FragmentSearchBinding
import com.example.savelifeapp.ui.home.viepager.bantu.BantuAdapater
import com.example.savelifeapp.ui.home.viepager.bantu.BantuViewModel
import com.example.savelifeapp.ui.home.viepager.bantu.DetailBantuActivity
import com.example.savelifeapp.ui.home.viepager.bantu.DetailBantuListener
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment(), DetailBantuListener {
    private var _binding: FragmentSearchBinding? = null
    private lateinit var list: ArrayList<Bantu>
    lateinit var searchAdapater: BantuAdapater
    val viewModel: BantuViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var rvSearch: RecyclerView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth  = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        observe()
        rvSearch = view.findViewById(R.id.rv_search)
        rvSearch.setHasFixedSize(true)
        rvSearch.layoutManager = LinearLayoutManager(requireContext())
        list = arrayListOf()
        searchAdapater = BantuAdapater(list,this)
        rvSearch.adapter = searchAdapater
        viewModel.getBantuPasien(list,searchAdapater)

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                call when press search button
                searchData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//             call when type
                list.clear()
                viewModel.getBantuPasien(list,searchAdapater)
                return false
            }
        })
    }

    private fun searchData(s: String?) {
        database.collectionGroup("MyRequest").whereEqualTo("name",s)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    list.clear()
                    if (error != null) {
                       Log.d("search",error.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        list.add(dc.document.toObject(Bantu::class.java))
                    }
                    searchAdapater.notifyDataSetChanged()
                }
            })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClicked(bantu: Bantu) {
        val intent = Intent(requireContext(), DetailBantuActivity::class.java)
        intent.putExtra("data",bantu)
        startActivity(intent)
    }
}