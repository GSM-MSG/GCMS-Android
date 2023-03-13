package com.msg.gcms.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.auth.CheckLoginStatusUseCase
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {
    private val _isLogin = MutableLiveData<Event>()
    val isLogin: LiveData<Event> get() = _isLogin

    fun checkIsLogin(context: Context) = viewModelScope.launch {
        checkLoginStatusUseCase().onSuccess {
            _isLogin.value = Event.Success
        }.onFailure {
            _isLogin.value = it.errorHandling { saveTokenInfoUseCase() }
        }
    }
}