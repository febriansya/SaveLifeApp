package com.example.savelifeapp.ui.signUp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.drawToBitmap
import androidx.navigation.findNavController
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.ActivitySignUpBinding
import com.example.savelifeapp.ui.login.LoginActivity
import com.example.savelifeapp.utils.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*

@AndroidEntryPoint
@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    val REQUEST_CODE = 100
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    lateinit var filepath: Uri
    private val userAppColection = Firebase.firestore.collection("UserApp")
    val viewModels: SignUpViewModel by viewModels()
    val TAG: String = "SignUp Activity"

    //    datauser
    private lateinit var nama: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var golDarah: String
    private lateinit var phone: String
    private lateinit var address: String
    private lateinit var data: String
    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, bloodType)
        binding.autoCompleteText.setAdapter(arrayAdapter)

//        inisialisasi firebase
        auth = FirebaseAuth.getInstance()
        observer()
        binding.icBack.setOnClickListener {
            onBackPressed()
        }
        binding.imgProfile.setOnClickListener {
            openCamera()
        }
        binding.textdate?.setOnClickListener {
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
        binding.signUp?.setOnClickListener {
            nama = binding.edtName.text.toString()
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            golDarah = binding.autoCompleteText.text.toString()
            phone = binding.edtPhone.text.toString()
            address = binding.edtAddress.text.toString()
            data = binding.textdate?.text.toString()
            image = binding.imgProfile.toString()
//            image = filepath.toString()
//            implementasi viewmodel disini
            if (validation()) {
                viewModels.register(
                    email = email,
                    password = password,
                    usersApp = getUserObj(),
                )
            }
        }
    }

    fun observer() {
        viewModels.register.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar2?.show()
                }
                is UiState.Failure -> {
                    toast(state.error)
                    binding.progressBar2?.hide()
                }
                is UiState.Success -> {
                    toast(state.data)
                    binding.progressBar2?.show()
                    auth.signOut()
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    fun getUserObj(): UsersApp {
        return UsersApp(
            uuid = "",
            nama = binding.edtName.text.toString(),
            email = binding.edtEmail.text.toString(),
            password = binding.edtPassword.text.toString(),
            golDarah = binding.autoCompleteText.text.toString(),
            hone = binding.edtPhone.text.toString(),
            address = binding.edtAddress.text.toString(),
            data = binding.textdate?.text.toString(),
            image = filepath.toString()
        )
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            applicationContext?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(
                        intent,
                        com.example.savelifeapp.ui.account.AccountFragment.REQUEST_CAMERA
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == com.example.savelifeapp.ui.account.AccountFragment.REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img").child("${UUID.randomUUID()}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        ref.putBytes(image)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            filepath = it
                            binding.imgProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    fun validation(): Boolean {
        var isValid = true
        when {
            binding.edtAddress.text.isNullOrEmpty() && binding.edtName.text.isNullOrEmpty() &&
                    binding.edtEmail.text.isNullOrEmpty() && binding.textdate?.text.isNullOrEmpty() && binding.autoCompleteText.text.isNullOrEmpty()
                    && binding.edtPassword.text.isNullOrEmpty() -> {
                toast(getString(R.string.all_fill_must_input))
                isValid = false
                return isValid
            }
            binding.edtName.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.enter_name))
                return isValid
            }
            binding.edtEmail.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.enter_email))
                return isValid
            }
            binding.autoCompleteText.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.enter_gol))
                return isValid
            }
            binding.textdate?.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.enter_textdate))
                return isValid
            }
            binding.imgProfile.drawable == null -> {
                toast(getString(R.string.image_must))
                isValid = false
                return isValid
            }
            !binding.edtEmail.text.toString().isValidEmail() -> {
                isValid = false
                toast(getString(R.string.invalid_email))
                return isValid
            }
            binding.edtPassword.text.toString().length < 8 -> {
                isValid = false
                toast(getString(R.string.password_invalid))
                return isValid
            }
        }
        return isValid
    }
}
