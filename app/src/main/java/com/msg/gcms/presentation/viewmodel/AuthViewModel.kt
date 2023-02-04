package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.auth.RefreshUseCase
import com.msg.gcms.domain.usecase.auth.SignInUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _postSignInRequest = MutableLiveData<Event>()
    val postSignInRequest: LiveData<Event> get() = _postSignInRequest

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    fun postSignInRequest(code: String) = viewModelScope.launch {
        signInUseCase(
            SignInRequest(code = code)
        ).onSuccess {
            Log.d(TAG, it.toString())
            saveToken(it)
            _postSignInRequest.value = Event.Success
        }.onFailure {
            _postSignInRequest.value = when (it) {
                is BadRequestException -> {
                    Log.d(TAG, "PostSignIn: $it.")
                    Event.BadRequest
                }
                is UnauthorizedException -> {
                    Log.d(TAG, "PostSignIn: $it")
                    Event.Unauthorized
                }
                is NotFoundException -> {
                    Log.d(TAG, "PostSignIn: $it")
                    Event.NotFound
                }
                else -> {
                    Log.d(TAG, "PostSignIn: $it")
                    Event.UnKnown
                }
            }
        }
    }

    private fun saveToken(response: SignInResponse) {
        GCMSApplication.prefs.apply {
            accessToken = response.accessToken
            refreshToken = response.refreshToken
            accessExp = response.accessExp
            refreshExp = response.refreshExp
        }
    }
}
