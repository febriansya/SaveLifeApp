package com.example.savelifeapp.ui.account

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.ActivityUpdateAccountBinding
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class UpdateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var userApp: UsersApp

    private val PICK_IMAGE_REQUEST = 100
    lateinit var filePath: Uri
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var storageRef: FirebaseStorage? = null
    private var userAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
        userAuth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance()


        getTextStandar()
        binding.imageView7.setOnClickListener {
            onBackPressed()
        }
        observer()
        binding.btnSimpan.setOnClickListener {
            viewModel.UpdateAccount(getUpdateUser())
            finish()
        }

        binding.textdate.setOnClickListener {
            textDate()
        }

        binding.imageChange.setOnClickListener {
            OpenGallery()
        }
    }

    //    Update Profile
    fun OpenGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
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

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
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
            }
    }


    fun textDate() {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.textdate!!.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun getTextStandar() {
        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, bloodType)
        binding.choose.setAdapter(arrayAdapter)
        val currentUser = userAuth?.currentUser?.uid.toString()
        val docRef = mFirebaseDatabaseInstance?.collection("UserApp")?.document(currentUser)
        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(UsersApp::class.java)
            val p = arrayAdapter.getPosition(user?.golDarah)
            binding.choose.setText(arrayAdapter.getItem(p).toString(), false)
            binding.fullName.setText(user?.nama)
            binding.emailAdress.setText(user?.email)
            binding.edtPhone.setText(user?.hone)
            binding.edtAdress.setText(user?.address)
            binding.textdate.setText(user?.data)
            binding.imageChange.setText(user?.image)
        }
    }

    private fun getUpdateUser(): UsersApp {
        userApp = UsersApp(
            nama = binding.fullName.text.toString(),
            address = binding.edtAdress.text.toString(),
            email = binding.emailAdress.text.toString(),
            golDarah = binding.choose.text.toString(),
            hone = binding.edtPhone.text.toString(),
            data = binding.textdate.text.toString(),
            image = filePath.toString()
        )
        return userApp
    }

    private fun observer() {
        viewModel.updateAccount.observe(this) { state ->
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
}