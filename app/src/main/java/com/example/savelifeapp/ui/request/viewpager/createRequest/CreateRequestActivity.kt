package com.example.savelifeapp.ui.request.viewpager.createRequest

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.jenisDarah
import com.example.savelifeapp.databinding.ActivityCreateRequestBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CreateRequestActivity : AppCompatActivity(),
    CreateRequestAdapter.golDarahListener {


    private val PICK_IMAGE_REQUEST = 100
    lateinit var filePath: Uri
    private var storageRef: FirebaseStorage? = null

    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var recyler: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val viewModels: CreateRequestViewModel by viewModels()
    private lateinit var image: String
    private lateinit var goldarah: String
    private var tampungValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        storageRef = FirebaseStorage.getInstance()

        recyler = binding.recylerOfJenis

        showRecylerList()
        observer()
        binding.btnLanjutkan.setOnClickListener {
            if (validation()) {
                viewModels.createRequest(getUserObj())
            }
        }
        binding.uploadBukti.setOnClickListener {
            OpenGallery()
        }
        getImageFromUrl()
    }

    private fun getImageFromUrl() {
        auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        val darahCurrent = Firebase.firestore.collection("UserApp").document(id.toString())
        darahCurrent.get().addOnCompleteListener {
            if (it.isSuccessful) {
                image = it.result.getString("image").toString()
            }
            Log.d("image", image)
        }
    }

    //    ngambil data dari adapterPosition untuk recylerview option jenis darah
    override fun onValueUbah(value: Int) {
        tampungValue = value
        val jenis = resources.getStringArray(R.array.jenis_darah)
        for (i in jenis.indices) {
            goldarah = jenis[value]
        }
        Log.d("goldarah", goldarah)
    }

    //    jenis darah
    private fun addData(): ArrayList<jenisDarah> {
        val stoks = ArrayList<jenisDarah>()
        val jenis = resources.getStringArray(R.array.jenis_darah)
        for (i in jenis.indices) {
            val stok = jenisDarah(
                jenis = jenis[i]
            )
            stoks.add(stok)
        }
        return stoks
    }

    private fun showRecylerList() {
        recyler.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val listCreateStok = CreateRequestAdapter(addData(), this)
        recyler.adapter = listCreateStok
    }


    private fun getUserObj(): CreateRequest {
        return CreateRequest(
            name = binding.edtNamePasien.text.toString(),
            golDarah = goldarah,
            lokasi = binding.edtLocationPasien.text.toString(),
            keterangan = binding.edtKeteranganPasien.text.toString(),
            image = image,
            whatsapp = binding.edtWa.text.toString(),
            photoBukti = filePath.toString(),
            timeStamp = Timestamp.now()
        )
    }

    private fun observer() {
        viewModels.createRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Process Loading")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    val intent = Intent(
                        this@CreateRequestActivity, HomeActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        when {
            binding.edtNamePasien.text.isNullOrEmpty() && binding.edtKeteranganPasien.text.isNullOrEmpty() && binding.edtLocationPasien.text.isNullOrEmpty() -> {
                toast("Semua Field tidak boleh kosong")
                isValid = false
                return isValid
            }
            binding.edtNamePasien.text?.isEmpty() == true -> {
                isValid = false
                toast("nama pasien tidak boleh kosong")
                return isValid
            }
            binding.edtLocationPasien.text!!.isEmpty() -> {
                isValid = false
                toast("Location tidak boleh kosong")
                return isValid
            }
            binding.edtWa.text!!.isEmpty() -> {
                isValid = false
                toast("Wa tidak boleh kosong")
                return isValid
            }
        }
        return isValid
    }
    //    ini untuk proses upload bukti foto pasien
    fun OpenGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val progres = ProgressDialog(this)
        progres.setTitle("Uploading")
        val ref = storageRef?.reference?.child("img")?.child("${UUID.randomUUID()}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val image = baos.toByteArray()
        ref?.putBytes(image)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result.let {
                            filePath = it
                            toast("photo success upload")
//                            binding.imageChange.setText(filePath.toString())
                        }
                    }
                }
            }?.addOnProgressListener {
                val totalKb: Long = it.totalByteCount / 1024
                val transferdeKb: Long = it.bytesTransferred / 1024
                progres.setMessage("$transferdeKb / $totalKb")
                if (transferdeKb == totalKb) {
                    progres.cancel()
                    binding.uploadBukti.setText("Bukti Berhasil di Upload")
                    binding.uploadBukti.background.setTint(R.color.teal_700)
                } else {
                    progres.show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                uploadImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
