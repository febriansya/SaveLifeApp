package com.example.savelifeapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.savelifeapp.databinding.ActivityLoginBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.signUp.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {


    //    this is fo firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore

    private var showPassword: Boolean = false
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //        inisialisasi firebase
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()

        if(auth.currentUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.hidePassword.setOnClickListener {
            if (!showPassword) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                showPassword = true
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                showPassword = false
            }
        }
        binding.Login.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password =binding.edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }
}
