package com.example.savelifeapp.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.ActivityChangePwdBinding
import com.example.savelifeapp.ui.login.forgotPassword.ResetPasswordViewModel
import com.example.savelifeapp.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePwdActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChangePwdBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseFirestore
    private lateinit var email: String

    val viewmodel: ResetPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      deklrasi get instance
        auth  = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

//        get data
        val data = database.collection("UserApp").document(auth.currentUser?.uid.toString()).get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    email = it.result.getString("email").toString()
                    binding.edtMail.setText(email.toString())
                }
            }

//        binding.edtMail.setText(email)

        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        observe()


        binding.apply {
            imageView21.setOnClickListener {
                onBackPressed()
            }
            btnSend.setOnClickListener {
                if (validation()) {
                    viewmodel.forgetPassword(binding.edtMail.text.toString())
                }
            }
        }
    }


    private fun observe() {
        viewmodel.forgotPassword.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    onBackPressed()
                }
            }
        }
    }

    fun validation(): Boolean {
        var isValid = true
        if (binding.edtMail.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_email))
        } else {
            if (!binding.edtMail.text.toString().isValidEmail()) {
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        return isValid
    }
}