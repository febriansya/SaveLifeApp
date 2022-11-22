package com.example.savelifeapp.ui.request.viewpager.receivedDetail

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.R
import com.example.savelifeapp.data.db.CalonEntity
import com.example.savelifeapp.data.db.RoomAppDb
import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.ActivityRecivedDetailBinding
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedAdapter
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder


@AndroidEntryPoint
class RecivedDetailActivity : AppCompatActivity() {

    private lateinit var adapter: ReceivedAdapter
    private lateinit var binding: ActivityRecivedDetailBinding
    private val viewModels: ReceivedViewModel by viewModels()
    private lateinit var calonPendonor: CalonPendonor
    private lateinit var usersApp: UsersApp
    private lateinit var copyReceived: Received
    private lateinit var auth: FirebaseAuth

    //    state shared preferences
    lateinit var preference: SharedPreferences
    val pref_detail_confirmation = "confirmation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecivedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        observer()
        observerTolak()
        val intent: Intent = intent

        var received: Received? = intent.getParcelableExtra("data_request")

        val calonPendnor = RoomAppDb.getAppDatabase(this)?.pendonorDao()
        val list = calonPendnor?.getPendonorData()
        val filter = list?.filter {
            it.id == received?.id
        }
        Log.d("cekData", filter.toString())
        if (filter != null) {
            if (filter.isNotEmpty()) {
                val status = filter.get(0).status.toString()
                if (status == "Ditolak") {
                    binding.idStatus.text =
                        "Kondisi Terakhir permintaan ditolak, silahkan pilih button jadi pendonor jika bersedia"
                    binding.idStatus.setTextColor(R.color.red_medium)
                } else {
                    binding.idStatus.text = "Status diterima"
                    binding.idStatus.setTextColor(android.R.color.holo_orange_light)
                }
            } else {
                binding.idStatus.text = "Belum Di Konfirmasi silahkan pilih salah satu Button"
                binding.idStatus.setTextColor(android.R.color.holo_orange_light)
            }
        } else {
            binding.idStatus.text = "Belum Di Konfirmasi silahkan pilih salah satu Button"
            binding.idStatus.setTextColor(android.R.color.holo_orange_light)
        }

        //            BUTTON TOLAK PERMINTAAN
        binding.btnTolak.setOnClickListener {
            viewModels.tolakRequest(
                getUpdateObj(),
                received?.idPengirim.toString(),
                received?.id.toString(),
            )
            val pendonorDao = RoomAppDb.getAppDatabase(this)?.pendonorDao()?.insertPendonor(
                CalonEntity(
//                    0,
                    received?.id.toString(),
                    received?.name.toString(),
                    received?.id.toString(),
                    "Ditolak"
                )
            )
            toast("Berhasil Di Tolak")
            finish()
        }

        binding.apply {
            namePasie.text = received?.name.toString()
            riLocation.text = received?.lokasi.toString()
            idGol.text = received?.golDarah.toString()
            idKeteranganReq.text = received?.keterangan.toString()
            namePengirim.text = received?.namaPengirim.toString()
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


//        buttin show image
        binding.showbukti.setOnClickListener {
            showBukti(received?.photoBukti.toString())
        }


//        BUTTON TERIMA PERMINTAAN
        binding.button2.setOnClickListener {
            viewModels.acceptRequestData(
                getUpdateObj(),
                received?.idPengirim.toString(),
                received?.id.toString(),
            )
//            ini untuk state button ketika sudah klik jadi pendonor
            val pendonorDao = RoomAppDb.getAppDatabase(this)?.pendonorDao()?.insertPendonor(
                CalonEntity(
//                    0,
                    received?.id.toString(),
                    received?.name.toString(),
                    received?.id.toString(),
                    "Bisa"
                )
            )
        }
    }

    private fun getUpdateObj(): CalonPendonor {
        auth = FirebaseAuth.getInstance()
        calonPendonor = CalonPendonor(
            id = auth.currentUser?.uid.toString(),
        )
        return calonPendonor
    }

    private fun observerTolak() {
        viewModels.acceptRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    finish()
                }
            }
        }
    }

    private fun observer() {
        viewModels.acceptRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    val intent =
                        Intent(this@RecivedDetailActivity, ConfirmationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun showBukti(image: String) {
        var inflater = LayoutInflater.from(this)
        var pupupview = inflater.inflate(R.layout.image_showing, null, false)
        var imgagee = pupupview.findViewById<ImageView>(R.id.image_tampil)
        Picasso.get().load(image).into(imgagee)
        var close = pupupview.findViewById<ImageView>(R.id.ic_close)
        var builder = PopupWindow(
            pupupview,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        builder.setBackgroundDrawable(getDrawable(R.drawable.background_showing))
        builder.animationStyle
        builder.showAtLocation(this.findViewById(R.id.detailReceived), Gravity.CENTER, 0, 0)
        close.setOnClickListener {
            builder.dismiss()
        }
    }
}