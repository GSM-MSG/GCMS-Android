package com.msg.gcms.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.auth.CheckLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase
) : ViewModel() {
    private val _isLoginAble = MutableLiveData<Boolean>()
    val isLoginAble: LiveData<Boolean> get() = _isLoginAble

    fun checkIsLogin(context: Context) = viewModelScope.launch {
        checkLoginStatusUseCase().onSuccess {
            _isLoginAble.value = it
        }.onFailure {
            Toast.makeText(context, "알 수 없는 오류가 발생했습니다", Toast.LENGTH_SHORT).show()
        }
    }
}