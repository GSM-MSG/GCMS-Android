package com.msg.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.base.di.GCMSApplication
import com.msg.gcms.domain.usecase.common.RefreshUseCase
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _gAuthCode = MutableLiveData<String>()
    val gAuthCode: LiveData<String> get() = _gAuthCode

    private val _isLoginInProgress = mutableStateOf(false)
    val isLoginInProgress: MutableState<Boolean> get() = _isLoginInProgress

    private val TAG = "google login"

    fun checkLogin(){
        viewModelScope.launch {
            try {
                val response = refreshUseCase()
                when(response.code()) {
                    in 200..299 -> {
                        _isLogin.value = true
                    }
                    else -> _isLogin.value = false
                }
            } catch (e: Exception) {
                Log.d("ERROR", "checkLogin: ${e.message}")
            }
        }
    }

    fun setGAuthCode(code: String) {
        _gAuthCode.value = code
    }

    private fun saveToken(accessToken: String, refreshToken: String) {
        GCMSApplication.prefs.apply {
            this.accessToken = accessToken
            this.refreshToken = refreshToken
        }
    }
}
