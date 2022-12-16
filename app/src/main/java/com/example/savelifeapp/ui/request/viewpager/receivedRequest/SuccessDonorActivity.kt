package com.example.savelifeapp.ui.request.viewpager.receivedRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.R

class SuccessDonorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_donor)
        supportActionBar?.hide()
    }
}