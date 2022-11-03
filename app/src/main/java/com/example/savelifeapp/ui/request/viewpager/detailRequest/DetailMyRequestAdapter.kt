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


class DetailMyRequestAdapter(private var CalonPendonor: ArrayList<CalonPendonor>) :
    RecyclerView.Adapter<DetailMyRequestAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.nama_calon_pendonor)
        var alamat: TextView = itemView.findViewById(R.id.alamat_calon_pendonor)
        var image: ImageView = itemView.findViewById(R.id.image_calon_pendonor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_calon_pendonor, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calonPendonor: CalonPendonor = CalonPendonor[position]
        holder.nama.text = calonPendonor.name.toString()
        holder.alamat.text = calonPendonor.alamat.toString()
        Picasso.get().load(calonPendonor.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return CalonPendonor.size
    }
}