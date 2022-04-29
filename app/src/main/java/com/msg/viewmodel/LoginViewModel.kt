package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.LoginResponse
import com.msg.gcms.domain.usecase.common.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    var errorMsg: String = ""
    fun postLogin(body: LoginRequest){
        viewModelScope.launch {
            try {
                val response = loginUseCase.postLogin(body)
                when(response.code()){
                    400 -> errorMsg = "잘못된 데이터"
                    403 -> errorMsg = "비밀번호가 맞지 않습니다"
                    200 -> saveKey(response.body())
                }
            } catch (e: Exception){
                Log.d("error", "postLogin: ${e.message}")
            }
        }
    }

    private fun saveKey(response: LoginResponse?){

    }
}