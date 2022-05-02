package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.LoginResponse
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.domain.usecase.common.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    var errorMsg: String = ""

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    fun postLogin(body: LoginRequest){
        viewModelScope.launch {
            try {
                val response = loginUseCase.postLogin(body)
                when(response.code()){
                    400 -> {
                        errorMsg = "잘못된 데이터"
                        _loginStatus.value = false
                    }
                    403 -> {
                        errorMsg = "비밀번호가 맞지 않습니다"
                        _loginStatus.value = false
                    }
                    201 -> {
                        Log.d("TAG", "postLogin: ")
                        _loginStatus.value = true
                        GCMSApplication.prefs.token = response.body()?.accessToken
                    }
                }
            } catch (e: Exception){
                Log.d("error", "postLogin: ${e.message}")
            }
        }
    }
}