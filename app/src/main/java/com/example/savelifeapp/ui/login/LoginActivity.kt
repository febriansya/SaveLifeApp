package com.example.savelifeapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivityLoginBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.login.forgotPassword.ResetPasswordActivity
import com.example.savelifeapp.ui.signUp.SignUpActivity
import com.example.savelifeapp.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    //    new method use mvvm
    val TAG: String = "SignUp"
    val viewmodel: LoginViewModel by viewModels()
    //    this is fo firebase


    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //        inisialisasi firebase
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

//        all editTextinput
        binding.lupaPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        observer()
        binding.Login.setOnClickListener {
            if (validation()) {
                viewmodel.login(
                    email = binding.edtEmail.text.toString(),
                    password = binding.edtPassword.text.toString()
                )
            }
        }
        binding.SignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observer() {
        viewmodel.login.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
//                    binding.Login.setText("")
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                    if (auth.currentUser?.isEmailVerified == true) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        toast("Please check your email spam")
                    }
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        when {
            binding.edtPassword.text.isNullOrEmpty() && binding.edtEmail.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.no_field))
            }
            binding.edtEmail.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.type_your_email))
                return isValid
            }
            !binding.edtEmail.text.toString().isValidEmail() -> {
                isValid = false
                toast(getString(R.string.invalid_email))
                return isValid
            }
            binding.edtPassword.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.invalid_password))
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

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
