package com.example.savelifeapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Stok


class StokAdapter(private var Stok: ArrayList<Stok>) :
    RecyclerView.Adapter<StokAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stok: TextView = itemView.findViewById(R.id.text_angka_donor)
        val jenis: TextView = itemView.findViewById(R.id.text_keterangan_jenis_darah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_stok_darah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stok: Stok = Stok[position]
        holder.stok.text = stok.jumlah.toString()
        holder.jenis.text = stok.golDarah
    }

    override fun getItemCount(): Int {
        return Stok.size
    }
}