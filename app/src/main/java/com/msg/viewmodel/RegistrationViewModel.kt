package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import com.msg.gcms.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase
) : ViewModel() {
    private val _emailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> get() = _emailCode

    private lateinit var email: String

    fun postEmailLogic(email: String) =
        viewModelScope.launch {
            this@RegistrationViewModel.email = email
            try {
                val response = useCase.postEmail(CodeIssuanceRequest(email))
                Log.d("TAG","${response.code()}, ${response.body()}")
                when(response.code()){
                    in 200..299 -> {}
                    in 400..599 -> {}
                }

            }catch (e : Exception){
                Log.d("TAG","error : $e")
            }
            //Log.d("TAG"," ${response.errorBody()}, ${response.isSuccessful}, ${response.headers()}, ${response.message()}")
            //Log.d("postEmail", response.code + response.message)
        }

    fun registrationLogic(email: String, password: String) {
        viewModelScope.launch {
            useCase.postRegistration(RegisterRequest(email, password))
        }
    }

    fun emailCheckLogic(emailCode: String) {
        viewModelScope.launch {
            useCase.headCheckCode(QueryString(email, emailCode))
        }
    }

    fun typeNumber(num: Int) {
        if (_emailCode.value?.length ?: 0 < 4) {
            val sb = StringBuilder(_emailCode.value ?: "")
            sb.append(num.toString())
            _emailCode.postValue(sb.toString())
        }
    }

    fun eraseNumber() {
        if (_emailCode.value?.isNotEmpty() == true) {
            val sb = StringBuilder(_emailCode.value ?: "")
            sb.deleteCharAt(sb.length - 1)
            _emailCode.postValue(sb.toString())
        }
    }

    fun clearNumber() {
        val sb = StringBuilder(_emailCode.value ?: "")
        sb.clear()
        _emailCode.postValue(sb.toString())
    }
}