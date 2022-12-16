package com.example.savelifeapp.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.R

class ChangePwdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwd)
        supportActionBar?.hide()
    }
}