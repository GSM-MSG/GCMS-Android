package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.base.di.GCMSApplication
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import com.msg.gcms.domain.usecase.common.RefreshUseCase
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    /*
    fun checkLogin(){
        viewModelScope.launch {
            try {
                refreshUseCase().onSuccess {
                    _isLogin.value = true
                    saveToken(it.body()!!)
                }.onFailure {
                    when (it) {
                        _isLogin.value = false
                        else -> _isLogin.value = false
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "checkLogin: ${e.message}")
            }
        }
    }

    fun sendIdTokenLogic(idToken: String) {
        viewModelScope.launch {
            try {
                registrationUseCase(RegisterRequest(
                    idToken = idToken
               )).onSuccess {
                    // Todo(Leehyeonbin) 여기도 스테이터스로 되어있음
                    _idTokenStatus.value = it.code()
                    saveToken(it.body()!!)
                }.onFailure {
                    when (it){
                        is BadRequestException -> Log.d(TAG, "sendIdTokenLogic: $it")
                        else -> Log.d(TAG, "sendIdTokenLogic: $it")
                    }
                }
            } catch (e: Exception) {
                Log.d("send IdToken error", "error : $e")
            }
        }
    } */

}
