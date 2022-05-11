package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.base.utils.Event
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase
) : ViewModel() {
    fun sendIdTokenLogic(idToken: String) {
        viewModelScope.launch {
            try {
                val response =
                    useCase.postRegistration(RegisterRequest(idToken = idToken))
                Log.d("TAG", "${response.code()}")
                when (response.code()) {

                }
            } catch (e: Exception) {
                Log.d("TAG", "error : $e")
            }
        }
    }
}
