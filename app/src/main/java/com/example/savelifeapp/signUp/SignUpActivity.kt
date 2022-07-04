package com.example.savelifeapp.signUp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivitySignUpBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    val REQUEST_CODE = 100
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, bloodType)
        binding.autoCompleteText.setAdapter(arrayAdapter)

        binding.icBack.setOnClickListener {
            onBackPressed()
        }
        binding.imgProfile.setOnClickListener {
            openGalleryImage()
        }
        binding.choseDate?.setOnClickListener {
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