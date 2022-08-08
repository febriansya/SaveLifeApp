package com.example.savelifeapp.ui.request.viewpager.mrequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Request

class RequestPagerFragment : Fragment() {

    private lateinit var rvRequest: RecyclerView
    private val list = ArrayList<Request>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRequest = view.findViewById(R.id.rv_MyRequest)
        rvRequest.setHasFixedSize(true)

        list.addAll(listMyRequests)
        showRecylerList()

    }

    private fun showRecylerList() {
        rvRequest.layoutManager = LinearLayoutManager(requireContext())
        val listRequestAdapter = MrequestAdapter(list)
        rvRequest.adapter = listRequestAdapter
    }

    private val listMyRequests: ArrayList<Request>
        get() {
            val namaRequest = resources.getStringArray(R.array.nama_pasien)
            val golDarah = resources.getStringArray(R.array.gol_darah)
            val listRequest = ArrayList<Request>()
            for (i in namaRequest.indices) {
                val request = Request(
                    namaRequest[i],
                    golDarah[i]
                )
                listRequest.add(request)
            }
            return listRequest
        }
}