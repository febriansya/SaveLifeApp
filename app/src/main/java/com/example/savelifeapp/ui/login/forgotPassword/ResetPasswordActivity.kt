package com.example.savelifeapp.ui.login.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivityResetPasswordBinding
import com.example.savelifeapp.databinding.FragmentHomeBinding
import com.example.savelifeapp.ui.login.LoginViewModel
import com.example.savelifeapp.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    val viewmodel: ResetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        observe()
        binding.button.setOnClickListener {
            if (validation()) {
                viewmodel.forgetPassword(binding.edtEmailReset.text.toString())
            }
        }
    }


    private fun observe() {
        viewmodel.forgotPassword.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.button.setText("")
                    binding.progressBar3.show()
                }
                is UiState.Failure -> {
                    binding.progressBar3.hide()
                    binding.button.setText("send")
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.button.setText("send")
                    binding.progressBar3.hide()
                    toast(state.data)
                }
            }
        }
    }


    fun validation(): Boolean {
        var isValid = true
        if (binding.edtEmailReset.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_email))
        } else {
            if (!binding.edtEmailReset.text.toString().isValidEmail()) {
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        return isValid
    }


}