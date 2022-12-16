package com.example.savelifeapp.ui.request.viewpager.receivedDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.data.db.CalonEntity
import com.example.savelifeapp.data.db.RoomAppDb
import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.HistoryDonors
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.databinding.ActivityConfirmationBinding
import com.example.savelifeapp.ui.account.HistoryDonorActivity
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder


@AndroidEntryPoint
class ConfirmationActivity : AppCompatActivity() {

    private val viewModels: ReceivedViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var calonPendonor: CalonPendonor
    private lateinit var history: HistoryDonors
    private lateinit var binding: ActivityConfirmationBinding
    private lateinit var received: Received

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        received = intent.getParcelableExtra("data_request")!!
        binding.apply {
            Picasso.get().load(received?.image).into(imgRecived)
            namePengirim.text = received?.name
            riLocation.text = received?.lokasi
            idGol.text = received?.golDarah
            idKeteranganReq.text = received?.keterangan

            imgBack.setOnClickListener {
                onBackPressed()
            }

            observer()

//            btnSudah.setOnClickListener {
//                viewModels.buatRiwayat(
//                    createHitory(),
//                    received?.id.toString(),
//                    received.name.toString()
//                )


//            }

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
                            this@ConfirmationActivity,
                            "Please install whatsapp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@ConfirmationActivity,
                        "" + e.stackTrace,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
//        update room ketika
        val calonPendnor = RoomAppDb.getAppDatabase(this)?.pendonorDao()
        val list = calonPendnor?.getPendonorData()
        val filter = list?.filter {
            it.id == received?.id
        }

//        val result = filter?.get(0)
//        toast(result.toString())
        binding.btnBatal.setOnClickListener {
            viewModels.tolakRequest(
                getUpdateObj(),
                received?.idPengirim.toString(),
                received?.id.toString(),
            )
//            val id = received?.id.toString()
//            val name = result?.name.toString()
//            val idRequest = result?.idRequest.toString()
//            val status = "Ditolak"
            calonPendnor?.insertPendonor(
                CalonEntity(
                    received?.id.toString(),
                    received?.name.toString(),
                    received?.id.toString(),
                    "Ditolak"
                )
            )
            viewModels.tolakRequest(
                getUpdateObj(),
                received?.idPengirim.toString(),
                received?.id.toString(),
            )
            finish()
        }
    }
//
//    fun createHitory(): HistoryDonors {
//        history = HistoryDonors(
//            namaPasien = received.name,
//        )
//        return history
//    }


    fun observer() {
        viewModels.buatRiwayat.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                }
            }
        }
    }

    fun getUpdateObj(): CalonPendonor {
        auth = FirebaseAuth.getInstance()
        calonPendonor = CalonPendonor(
            idPendonor = auth.currentUser?.uid.toString()
        )
        return calonPendonor
    }
}

