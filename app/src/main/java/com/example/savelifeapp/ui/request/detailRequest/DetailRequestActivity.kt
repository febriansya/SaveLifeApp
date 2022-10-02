package com.example.savelifeapp.ui.request.detailRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.ActivityDetailRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRequestActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_request)

    }
}