package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
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

    private fun saveToken(response: SignInResponse){
        GCMSApplication.prefs.apply {
            accessToken = response.accessToken
            refreshToken = response.refreshToken
        }
    }
}
