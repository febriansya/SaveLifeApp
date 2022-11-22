package com.example.savelifeapp.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savelifeapp.databinding.ActivityHistoryDonorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDonorActictiy : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDonorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()





    }
}