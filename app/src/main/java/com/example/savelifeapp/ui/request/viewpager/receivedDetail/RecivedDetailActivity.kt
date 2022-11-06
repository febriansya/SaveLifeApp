package com.example.savelifeapp.ui.request.viewpager.receivedDetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.ActivityRecivedDetailBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder


@AndroidEntryPoint
class RecivedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecivedDetailBinding
    private val viewModels: ReceivedViewModel by viewModels()
    private lateinit var calonPendonor: CalonPendonor
    private lateinit var usersApp: UsersApp
    private lateinit var copyReceived: Received
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecivedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        observer()

        val intent: Intent = intent
        var received: Received? = intent.getParcelableExtra("data_request")
        if (received != null) {
            copyReceived = received
        }
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


        binding.button2.setOnClickListener {
            viewModels.acceptRequestData(
                getUpdateObj(),
                received?.idPengirim.toString(),
                received?.id.toString(),

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
                    val intent = Intent(this@RecivedDetailActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}