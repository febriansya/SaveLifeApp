package com.example.savelifeapp.home.ui.home.viepager.kegiatan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Kegiatan

class KegiatanAdapater(private val listSampleKegiatan: ArrayList<Kegiatan>) :
    RecyclerView.Adapter<KegiatanAdapater.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_kegiatan)
        var namaKegiatan: TextView = itemView.findViewById(R.id.tv_nama_tempat_item_kegiatan)
        var namaLokasiKegiatan: TextView = itemView.findViewById(R.id.tv_item_home_lokasi_kegiatan)
        var tanggalKegiatan: TextView = itemView.findViewById(R.id.tv_tanggal_home_kegiatan)
        var jamKegiatan: TextView = itemView.findViewById(R.id.tv_jam_home_kegiatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_kegiatan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, lokasi, tanggal, jam, profile) = listSampleKegiatan[position]
        holder.namaKegiatan.text = name
        holder.jamKegiatan.text = jam
        holder.namaLokasiKegiatan.text = lokasi
        holder.tanggalKegiatan.text = tanggal
        holder.imgPhoto.setImageResource(profile)
    }
    override fun getItemCount(): Int {
        return listSampleKegiatan.size
    }
}