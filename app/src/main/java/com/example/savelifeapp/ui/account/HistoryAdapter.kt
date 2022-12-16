package com.example.savelifeapp.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.HistoryDonors
import java.text.SimpleDateFormat
import java.util.Date

class HistoryAdapter(
    private val History: ArrayList<HistoryDonors>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.nama_pasien)
        var tanggal: TextView = itemView.findViewById(R.id.id_tanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history: HistoryDonors = History[position]

        val timestamp = history.timeStamp as com.google.firebase.Timestamp
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(milliseconds)
        val dat = sdf.format(netDate).toString()
        holder.name.text = history.jenisDonor
        holder.tanggal.text = dat
    }

    override fun getItemCount(): Int {
        return History.size
    }
}

interface sizeItem {
    fun itemSize(History: ArrayList<HistoryDonors>)
}
