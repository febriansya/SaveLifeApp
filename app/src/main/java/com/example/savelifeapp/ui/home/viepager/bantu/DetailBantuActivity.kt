package com.example.savelifeapp.ui.home.viepager.bantu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.Bantu
import com.example.savelifeapp.databinding.ActivityDetailBantuBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder


@AndroidEntryPoint
class DetailBantuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBantuBinding
    private lateinit var received:Bantu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        received = intent.getParcelableExtra("data")!!
        binding = ActivityDetailBantuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
//            Picasso.get().load(received.image).into(imgPengirim)
            namePengirim.text = received.namaPengirim
            namePasien.text  = received.name
            idLokasiPasien.text =received.lokasi
            golDarah.text = received.golDarah
            keteranganPasien.text = received.keterangan


            waDetail.setOnClickListener {
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
                            this@DetailBantuActivity,
                            "Please install whatsapp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@DetailBantuActivity,
                        "" + e.stackTrace,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            button5.setOnClickListener {

                val inflater = LayoutInflater.from(this@DetailBantuActivity)
                val pupupview = inflater.inflate(R.layout.image_showing, null, false)
                val imgagee = pupupview.findViewById<ImageView>(R.id.image_tampil)
                Picasso.get().load(received.photoBukti).into(imgagee)
                val close = pupupview.findViewById<ImageView>(R.id.ic_close)
                val builder = PopupWindow(
                    pupupview,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    true
                )
                builder.setBackgroundDrawable(getDrawable(R.drawable.background_showing))
                builder.showAtLocation(this.detailBantu, Gravity.CENTER, 0, 0)
                close.setOnClickListener {
                    builder.dismiss()
                }
            }
            }
        }
    }
