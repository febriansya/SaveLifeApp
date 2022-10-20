package com.example.savelifeapp.ui.home.viepager.kegiatan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Kegiatan
import com.squareup.picasso.Picasso

class KegiatanAdapater(private val listSampleKegiatan: ArrayList<Kegiatan>) :
    RecyclerView.Adapter<KegiatanAdapater.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_bantu)
        var namaKegiatan: TextView = itemView.findViewById(R.id.tv_nama_pasien_bantu)
        var namaLokasiKegiatan: TextView = itemView.findViewById(R.id.tv_lokasi_pasien_bantu)
        var tanggalKegiatan: TextView = itemView.findViewById(R.id.tv_tanggal_home_kegiatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_kegiatan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data: Kegiatan = listSampleKegiatan[position]
        holder.namaKegiatan.text = data.nameKegiatan
        holder.namaLokasiKegiatan.text = data.lokasiKegiatan
        holder.tanggalKegiatan.text = data.tanggalKegiatan
        Picasso.get().load(data.img).into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listSampleKegiatan.size
    }
}