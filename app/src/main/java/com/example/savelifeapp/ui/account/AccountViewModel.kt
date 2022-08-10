package com.example.savelifeapp.ui.account


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.model.Profile
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.ProfileRespository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: ProfileRespository,
    val authRepository: AuthRepository
) : ViewModel() {
    private val _profile = MutableLiveData<List<Profile>>()
    val profile:LiveData<List<Profile>>
    get() = _profile

    fun getProfile(){
        _profile.value = repository.getProfile()
    }

    //    logout
    private val _logout = MutableLiveData<UiState<String>>()
    val logout: LiveData<UiState<String>>
        get() = _logout

    fun logout() {
        _logout.value = UiState.Loading
        authRepository.logout("current") {
            _logout.value = it
        }
    }
}