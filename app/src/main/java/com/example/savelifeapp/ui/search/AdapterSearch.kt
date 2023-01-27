package com.example.savelifeapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Bantu
import com.squareup.picasso.Picasso

class AdapterSearch(
    private val listBantuSearch: ArrayList<Bantu>,
    private val listener: SearchFragment
) : RecyclerView.Adapter<AdapterSearch.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_recived)
        var namaPasien: TextView = itemView.findViewById(R.id.tv_nama_pasien_bantu)
        var lokasiPasien: TextView = itemView.findViewById(R.id.tv_lokasi_pasien_bantu)
        var golDarahPasien: TextView = itemView.findViewById(R.id.gol_darah_pasien)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_view, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data:Bantu  = listBantuSearch[position]
        holder.namaPasien.text = data.name
        holder.golDarahPasien.text = data.golDarah
        holder.lokasiPasien.text = data.lokasi
        Picasso.get().load(data.image).into(holder.imgPhoto)

        holder.itemView.setOnClickListener {
            listener.itemClicked(data)
        }
    }

    override fun getItemCount(): Int {
       return listBantuSearch.size
    }
}

interface DetailSearchListener {
    fun itemClicked(bantu: Bantu)
}