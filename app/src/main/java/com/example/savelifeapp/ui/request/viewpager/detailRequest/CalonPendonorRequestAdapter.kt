package com.example.savelifeapp.ui.request.viewpager.detailRequest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.CalonPendonor
import com.squareup.picasso.Picasso


class CalonPendonorRequestAdapter(
    private var CalonPendonor: ArrayList<CalonPendonor>,
    private val listener: CalonPendonorClickListener
) :
    RecyclerView.Adapter<CalonPendonorRequestAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.nama_calon_pendonor)
        var alamat: TextView = itemView.findViewById(R.id.alamat_calon_pendonor)
        var image: ImageView = itemView.findViewById(R.id.image_calon_pendonor)
        var btnWa: ImageView = itemView.findViewById(R.id.btn_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_calon_pendonor, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calonPendonor: CalonPendonor = CalonPendonor[position]
        holder.nama.text = calonPendonor.nama.toString()
        holder.alamat.text = calonPendonor.address.toString()

        Picasso.get().load(calonPendonor.image).into(holder.image)
        holder.btnWa.setOnClickListener {
            listener.onItemClicked(calonPendonor)
        }
    }

    override fun getItemCount(): Int {
        return CalonPendonor.size
    }
}

interface CalonPendonorClickListener {
    fun onItemClicked(calonPendonor: CalonPendonor)
}