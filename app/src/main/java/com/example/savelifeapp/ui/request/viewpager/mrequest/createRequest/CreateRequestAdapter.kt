package com.example.savelifeapp.ui.request.viewpager.mrequest.createRequest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.jenisDarah

class CreateRequestAdapter(private var jenisGol: ArrayList<jenisDarah>,val onClickValue:golDarahListener) :
    RecyclerView.Adapter<CreateRequestAdapter.ViewHolder>() {

    private var selectItemPosition: Int = 0

    interface golDarahListener {
        fun onValueUbah(value:Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textGolongan: TextView = itemView.findViewById(R.id.jenis_gol_create)
        val image: ImageView = itemView.findViewById(R.id.imageView5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_create_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (jenis) = jenisGol[position]
        holder.textGolongan.text = jenis
        holder.itemView.setOnClickListener {
            selectItemPosition = holder.adapterPosition
            onClickValue.onValueUbah(selectItemPosition)
            notifyDataSetChanged()
        }
        if (selectItemPosition == position) {
            holder.image.setImageResource(R.drawable.background_darah_selected)

        } else {
            holder.image.setImageResource(R.drawable.background_darah)
        }
    }

    override fun getItemCount(): Int {
        return jenisGol.size
    }
}


