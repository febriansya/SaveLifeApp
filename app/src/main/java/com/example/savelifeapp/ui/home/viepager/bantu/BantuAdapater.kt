package com.example.savelifeapp.ui.home.viepager.bantu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.Bantu


class BantuAdapater(private val Bantu: ArrayList<Bantu>) :
    RecyclerView.Adapter<BantuAdapater.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_bantu)
        var namaPasien: TextView = itemView.findViewById(R.id.tv_nama_pasien_bantu)
        var golDarah: TextView = itemView.findViewById(R.id.tv_gol_pasien_bantu)
        var lokasiPasien: TextView = itemView.findViewById(R.id.tv_lokasi_pasien_bantu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_bantu, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, lokasi, gol, profile) = Bantu[position]
        holder.namaPasien.text = name
        holder.golDarah.text = gol
        holder.lokasiPasien.text = lokasi
        holder.imgPhoto.setImageResource(profile)
    }

    override fun getItemCount(): Int {
        return Bantu.size
    }
}