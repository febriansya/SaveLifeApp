package com.example.savelifeapp.home.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R


class RecyclerAdapterStokDarah(private val dataset: Array<String?>) :
    RecyclerView.Adapter<RecyclerAdapterStokDarah.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textAngka: TextView = view.findViewById(R.id.text_angka_donor)
        var textKeteranganJenisDarah: TextView = view.findViewById(R.id.text_keterangan_jenis_darah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_stok_darah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textAngka.text = dataset[position].toString()
        holder.textKeteranganJenisDarah.text = "AB+"
    }
    override fun getItemCount(): Int {
        return dataset.size
    }



}