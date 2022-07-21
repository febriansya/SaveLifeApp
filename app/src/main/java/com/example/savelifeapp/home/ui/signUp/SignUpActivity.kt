package com.example.savelifeapp.home.ui.signUp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.savelifeapp.R
import com.example.savelifeapp.data.UsersApp
import com.example.savelifeapp.databinding.ActivitySignUpBinding
import com.example.savelifeapp.home.ui.login.LoginActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    val REQUEST_CODE = 100
    private lateinit var binding: ActivitySignUpBinding


    //    this is fo firebase
    private lateinit var auth: FirebaseAuth
    private val userAppColection = Firebase.firestore.collection("UserApp")


    //    datauser
    private lateinit var nama: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var golDarah: String
    private lateinit var phone: String
    private lateinit var address: String
    private lateinit var data: String

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

        binding.icBack.setOnClickListener {
            onBackPressed()
        }

        val getImage = binding.imgProfile.setOnClickListener {
            openGalleryImage()
        }
        val choseDate = binding.choseDate?.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
                .also {
                    title = "Pick Date"
                }
            val datePicker = builder.build()
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                binding.textdate?.text =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}- " + "${calendar.get(Calendar.MONTH) + 1}-${
                        calendar.get(Calendar.YEAR)
                    }"
            }
            datePicker.show(supportFragmentManager, "MyTAG")
        }

        binding.signUp?.setOnClickListener {

            nama = binding.edtName.text.toString()
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            golDarah = binding.autoCompleteText.text.toString()
            phone = binding.edtPhone.text.toString()
            address = binding.edtAddress.text.toString()
            data = binding.textdate?.text.toString()

            if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && golDarah.isNotEmpty() && phone.isNotEmpty()
                && address.isNotEmpty() && data.isNotEmpty()
            ) {


//                init harus menggunakn
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val usersAp = UsersApp(
                            auth.uid.toString(),
                            nama,
                            email,
                            password,
                            golDarah,
                            phone,
                            address,
                            data
                        )

//                      firestore add data users with id auth  -> id auth dan id firestore jadi sama
                        userAppColection.document(auth.uid.toString()).set(usersAp)

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed !!", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun openGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.imgProfile.setImageURI(data?.data) // handle chosen image
        }
    }
}