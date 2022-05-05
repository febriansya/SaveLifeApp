package com.example.savelifeapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.example.savelifeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var showPassword: Boolean = false
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hidePassword.setOnClickListener {
            if (showPassword == false) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                showPassword = true
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                showPassword = false
            }
        }
    }
}
