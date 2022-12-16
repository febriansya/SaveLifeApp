package com.example.savelifeapp.ui.home.viepager.kegiatan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.databinding.ActivityDetailKegiatanBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailKegiatanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKegiatanBinding
    private lateinit var received: Kegiatan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val intent: Intent = intent
        received = intent.getParcelableExtra("data_kegiatan")!!
        Log.d("tes",received.toString())

        binding.apply {
            imageView9.setOnClickListener {
                onBackPressed()
            }
            namaKegiatan.text = received.nameKegiatan
            lokasiKegiatan.text= received.lokasiKegiatan


            val timestamp = received.timeStamp as com.google.firebase.Timestamp
            val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(milliseconds)
            val dat = sdf.format(netDate).toString()
            tanggalKegiatan.text = dat
            jamKegiatan.text = received.jamKegiatan
            keteranganKegiatan.text = received.infoLanjut
            button3.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(received.maps)
                startActivity(i)
            }
        }
    }
}