package com.example.savelifeapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val auth: AuthRepository
) : ViewModel() {

}