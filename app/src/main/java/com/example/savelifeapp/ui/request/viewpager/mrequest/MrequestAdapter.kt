package com.example.savelifeapp.ui.request.viewpager.mrequest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Request

class MrequestAdapter(private val Request: ArrayList<Request>) :
    RecyclerView.Adapter<MrequestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tv_nama_pasien_request)
        var golDarahRequest: TextView = itemView.findViewById(R.id.gol_darah_request)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (nama, golDarah, lokasi, keterangan) = Request[position]
        holder.name.text = nama
        holder.golDarahRequest.text = golDarah
    }

    override fun getItemCount(): Int {
        return Request.size
    }

}