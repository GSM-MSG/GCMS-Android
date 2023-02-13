package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.auth.LogoutUseCase
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.auth.SignInUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.util.removeDot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {
    private val _postSignInRequest = MutableLiveData<Event>()
    val postSignInRequest: LiveData<Event> get() = _postSignInRequest

    fun postSignInRequest(code: String) = viewModelScope.launch {
        signInUseCase(
            SignInRequest(code = code)
        ).onSuccess {
            saveToken(it)
            _postSignInRequest.value = Event.Success
        }.onFailure {
            _postSignInRequest.value = when (it) {
                is BadRequestException -> Event.BadRequest
                is UnauthorizedException -> Event.Unauthorized
                is NotFoundException -> Event.NotFound
                else -> Event.UnKnown
            }
        }
    }

    fun logoutRequest() = viewModelScope.launch {
        saveTokenInfoUseCase("", "", "", "")
        logoutUseCase()
            .onSuccess {
                showLog("Logout: Success!")
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> showLog("Logout: UnauthorizedException")
                    is NotFoundException -> showLog("Logout: NotFoundException")
                    else -> showLog("Logout: Unknown, status: $it")
                }
            }
    }

    private fun saveToken(response: SignInResponse) = viewModelScope.launch {
        saveTokenInfoUseCase(
            accessToken = response.accessToken.removeDot(),
            refreshToken = response.refreshToken.removeDot(),
            accessExp = response.accessExp.removeDot(),
            refreshExp = response.refreshExp.removeDot()
        )
    }

    private fun showLog(msg: String) {
        Log.d("AuthViewModel", msg)
    }
}
