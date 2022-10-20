package com.example.savelifeapp.ui.request.viewpager.mrequest

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
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.databinding.FragmentRequestPagerBinding
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestActivity
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestPagerFragment : Fragment() {
    private lateinit var rvRequest: RecyclerView
    private lateinit var list:ArrayList<CreateRequest>
    private var _binding: FragmentRequestPagerBinding? = null
    lateinit var requestPagerAdapater: MrequestAdapter
    val viewModel: CreateRequestViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRequestPagerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRequest = view.findViewById(R.id.rv_MyRequest)
        rvRequest.setHasFixedSize(true)

        rvRequest.layoutManager = LinearLayoutManager(requireContext())
        rvRequest.setHasFixedSize(true)
        list = arrayListOf()

        requestPagerAdapater = MrequestAdapter(list)
        rvRequest.adapter = requestPagerAdapater
        observe()
        viewModel.getDataRequest(list, requestPagerAdapater)
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireActivity(), CreateRequestActivity::class.java)
            startActivity(intent)
        }
    }

    fun observe() {
        viewModel.getRequest.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    Log.d("loading","loading")
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