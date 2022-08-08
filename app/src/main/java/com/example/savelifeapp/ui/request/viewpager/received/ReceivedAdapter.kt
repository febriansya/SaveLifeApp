package com.example.savelifeapp.ui.request.viewpager.received

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Received

class ReceivedAdapter(private val Received: ArrayList<Received>) :
    RecyclerView.Adapter<ReceivedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.img_bantu)
        var namaPasien: TextView = itemView.findViewById(R.id.tv_nama_pasien_bantu)
        var golonganDarah: TextView = itemView.findViewById(R.id.tv_lokasi_pasien_bantu)
        var alamatPasien: TextView = itemView.findViewById(R.id.tv_alamat_bantu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (nama, profile, alamat, golDarah) = Received[position]
        holder.namaPasien.text = nama
        holder.image.setImageResource(profile)
        holder.alamatPasien.text = alamat
        holder.golonganDarah.text = golDarah
    }

    override fun getItemCount(): Int {
        return Received.size
    }


}