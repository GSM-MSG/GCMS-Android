package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.base.di.GCMSApplication
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
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

    private val _idTokenStatus = MutableLiveData<Int>()
    val idTokenStatus: LiveData<Int> get() = _idTokenStatus

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val TAG = "google login"

    private fun saveToken(response: RegisterResponse){
        GCMSApplication.prefs.apply {
            accessToken = response.accessToken
            refreshToken = response.refreshToken
            Log.d(TAG,"access : $accessToken")
            Log.d(TAG,"refresh : $refreshToken")
        }
    }

    fun checkLogin(){
        viewModelScope.launch {
            try {
                val response = refreshUseCase()
                when(response.code()) {
                    in 200..299 -> {
                        _isLogin.value = true
                        saveToken(response.body()!!)
                    }
                    else -> _isLogin.value = false
                }
            } catch (e: Exception) {
                Log.d("ERROR", "checkLogin: ${e.message}")
            }
        }
    }

    fun sendIdTokenLogic(idToken: String) {
        viewModelScope.launch {
            try {
                val response = registrationUseCase.postRegistration(RegisterRequest(idToken = idToken))
                when (response.code()) {
                    in 200..299 -> {
                        printStatus(response.code())
                        _idTokenStatus.value = response.code()
                        saveToken(response.body()!!)
                    }
                    400 -> {
                        printStatus(response.code())
                        _idTokenStatus.value = response.code()
                    }
                    else -> {
                        printStatus(response.code())
                        _idTokenStatus.value = response.code()
                    }
                }
            } catch (e: Exception) {
                Log.d("send IdToken error", "error : $e")
            }
        }
    }

    private fun printStatus(code : Int) {
        Log.d(TAG,"status : $code")
    }
}
