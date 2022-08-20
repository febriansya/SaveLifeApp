package com.example.savelifeapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivityLoginBinding
import com.example.savelifeapp.ui.HomeActivity
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
    private lateinit var auth: FirebaseAuth
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

        if (auth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.hidePassword.setOnClickListener {
            showPassword()
        }

        observer()
//        use viewmodel
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
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        when {
            binding.edtPassword.text.isNullOrEmpty()&&binding.edtEmail.text.isNullOrEmpty() ->{
                isValid= false
             toast(getString(R.string.no_field))
            }
            binding.edtEmail.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.type_your_email))
                return  isValid
            }
            !binding.edtEmail.text.toString().isValidEmail() -> {
                isValid = false
                toast(getString(R.string.invalid_email))
                return  isValid
            }
            binding.edtPassword.text.isNullOrEmpty() -> {
                isValid = false
                toast(getString(R.string.invalid_password))
                return  isValid
            }
            binding.edtPassword.text.toString().length < 8 -> {
                isValid = false
                toast(getString(R.string.password_invalid))
                return  isValid
            }
        }
        return isValid
    }

    fun showPassword(): Boolean {
        var showPassword: Boolean = false
        if (!showPassword) {
            binding.edtPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            showPassword = true
        } else {
            binding.edtPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            showPassword = false
        }
        return showPassword
    }


    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }
}
