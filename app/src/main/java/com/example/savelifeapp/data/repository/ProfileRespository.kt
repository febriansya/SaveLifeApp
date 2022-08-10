package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Profile

interface ProfileRespository {
    fun getProfile(): List<Profile>
}