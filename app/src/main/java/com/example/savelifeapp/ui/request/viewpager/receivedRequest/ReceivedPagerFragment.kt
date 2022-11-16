package com.example.savelifeapp.ui.request.viewpager.receivedRequest


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.db.RoomAppDb
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.ui.request.viewpager.receivedDetail.ConfirmationActivity
import com.example.savelifeapp.ui.request.viewpager.receivedDetail.RecivedDetailActivity
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReceivedPagerFragment() : Fragment(), RecylerViewClickListener {
    private lateinit var rvReceived: RecyclerView
    private lateinit var list: ArrayList<Received>
    private lateinit var receivedAdapter: ReceivedAdapter
    val viewmodel: ReceivedViewModel by viewModels()
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
        rvReceived.layoutManager = LinearLayoutManager(requireContext())
        rvReceived.setHasFixedSize(true)
        list = arrayListOf()
//        panggil listener untuk fungsi recylerviewListener
        receivedAdapter = ReceivedAdapter(list, "")
        receivedAdapter.listener = this
        rvReceived.adapter = receivedAdapter
        observer()
        viewmodel.getReceivedData(list, receivedAdapter)
    }


    fun observer() {
        viewmodel.getRequest.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    Log.d("loading", "loading")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    Log.d("tesObserver", state.data.toString())
                }
            }
        }
    }

    override fun onItemClicked(view: View, received: Received) {
        val calonPendnor = RoomAppDb.getAppDatabase(requireContext())?.pendonorDao()
        val list = calonPendnor?.getPendonorData()
//        ambil data dari database pendonor
//        cek apakah kosong atau tidak
        if (list?.isEmpty() == true) {
            val intent = Intent(
                requireContext(),
                RecivedDetailActivity::class.java
            )
            intent.putExtra("data_request", received)
            startActivity(intent)
        } else {
            val filter = list?.filter {
                it.idRequest == received.id
            }
            Log.d("filter", filter.toString())

//            cek jika user belum klik jadi pendonor maka niali filter akan kosong kalau sudah lanjut ke else
            if (filter?.isEmpty() == true) {
                val intent = Intent(
                    requireContext(),
                    RecivedDetailActivity::class.java
                )
                intent.putExtra("data_request", received)
                startActivity(intent)
            } else {
//                ambil data dari filter dengan id request
                val idFromFilter = filter?.get(0)
                val finalId = idFromFilter?.idRequest
//                cek apakah yang di klik dengan final id sama posisi disesuaikan dengan item yang di klik
                if (received.id == finalId && idFromFilter?.status == "Bisa") {
                    val intent = Intent(
                        requireContext(),
                        ConfirmationActivity::class.java
                    )
                    intent.putExtra("data_request", received)
                    startActivity(intent)
                } else {
//                    arahkan ke detail
                    val intent = Intent(
                        requireContext(),
                        RecivedDetailActivity::class.java
                    )
                    intent.putExtra("data_request", received)
                    startActivity(intent)
                }
            }
        }
    }
}