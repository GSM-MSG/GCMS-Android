package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase
) : ViewModel() {

    private val _idTokenStatus = MutableLiveData<Int>()
    val idTokenStatus: LiveData<Int> get() = _idTokenStatus

    fun sendIdTokenLogic(idToken: String) {
        viewModelScope.launch {
            try {
                val response =
                    useCase.postRegistration(RegisterRequest(idToken = idToken))
                Log.d("TAG", "${response.code()}")
                when (response.code()) {
                    in 200..299 -> {
                        Log.d("Oauth-status", "status : ${response.code()}")
                        _idTokenStatus.value = response.code()
                    }
                    else -> {
                        Log.d("Oauth-status", "error status: ${response.code()}")
                        _idTokenStatus.value = response.code()
                    }
                }
            } catch (e: Exception) {
                Log.d("send IdToken error", "error : $e")
            }
        }
    }
}
