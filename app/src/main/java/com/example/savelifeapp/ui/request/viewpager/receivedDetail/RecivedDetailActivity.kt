package com.example.savelifeapp.ui.request.viewpager.receivedDetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.databinding.ActivityRecivedDetailBinding
import com.squareup.picasso.Picasso
import java.net.URLEncoder

class RecivedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecivedDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecivedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        val intent: Intent = intent
        var received: Received? = intent.getParcelableExtra("data_request")
        binding.apply {
            namePasie.text = received?.name.toString()
            riLocation.text = received?.lokasi.toString()
            idGol.text = received?.golDarah.toString()
            idKeteranganReq.text = received?.keterangan.toString()
            Picasso.get().load(received?.image).into(imgRecived)
//            code dibawah ini untuk intent ke whatsapp
            intentWa.setOnClickListener {
                try {
                    val packageManager = getPackageManager()
                    val i = Intent(Intent.ACTION_VIEW)
                    val url =
                        "https://api.whatsapp.com/send?phone=" + "+62${received?.whatsapp}+text=" + URLEncoder.encode(
                            "hello assalamualaikum"
                        )
                    i.setPackage("com.whatsapp")
                    i.data = Uri.parse(url)
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i)
                    } else {
                        Toast.makeText(
                            this@RecivedDetailActivity,
                            "Please install whatsapp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@RecivedDetailActivity,
                        "" + e.stackTrace,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        Log.d("tes", received?.whatsapp.toString())
    }
}