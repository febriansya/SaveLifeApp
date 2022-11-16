package com.example.savelifeapp.ui.request.viewpager.receivedRequest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.db.RoomAppDb
import com.example.savelifeapp.data.model.Received
import com.squareup.picasso.Picasso


class ReceivedAdapter(
    private val Received: ArrayList<Received>? = null,
    private val idReceived: String? = null
) :
    RecyclerView.Adapter<ReceivedAdapter.ViewHolder>() {
    private val context: Context? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.img_recived)
        var namaPasien: TextView = itemView.findViewById(R.id.tv_nama_pasien_bantu)
        var golonganDarah: TextView = itemView.findViewById(R.id.tv_lokasi_pasien_bantu)
        var alamatPasien: TextView = itemView.findViewById(R.id.tv_alamat_bantu)
    }

    lateinit var listener: RecylerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val received: Received = Received!![position]
        holder.namaPasien.text = received.name
        holder.alamatPasien.text = received.lokasi
        holder.golonganDarah.text = received.golDarah
        Picasso.get().load(received.image).into(holder.image)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(it, Received[position])
        }

//        when (idReceived) {
//            "Bisa" -> {
//                holder.status.text = "DITERIMA"
//                holder.status.setTextColor(R.color.purple_200)
//            }
//            "Ditolak" -> {
//                holder.status.text = "DITOLAK"
//                holder.status.setTextColor(R.color.red_medium)
//            }
//            else -> {
//                holder.status.text = "Anda Belum Konfirmasi"
//                holder.status.setTextColor(R.color.red_medium)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return Received!!.size
    }
}

//    ini untuk click listener ke detail dan akan di implementasikan di fragment
interface RecylerViewClickListener {
    fun onItemClicked(view: View, received: Received)
//    var simpan: Received
}