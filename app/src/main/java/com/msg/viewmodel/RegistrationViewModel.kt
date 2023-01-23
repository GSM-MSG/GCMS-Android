package com.msg.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gauthsignin.GAuth
import com.msg.gcms.BuildConfig
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
                val response = refreshUseCase.postRefresh()
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

    fun getGAuthToken(context: Context) {
        GAuth.getGAuthTokenRequest(
            code = gAuthCode.value!!,
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            redirectUri = BuildConfig.REDIRECT_URI
        ) {
            if (it.status == 200)
                saveToken(it.accessToken!!, it.refreshToken!!)
            else {
                Toast.makeText(context, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
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
            Log.d(TAG, "access : $accessToken")
            Log.d(TAG, "refresh : $refreshToken")
        }
    }
}
