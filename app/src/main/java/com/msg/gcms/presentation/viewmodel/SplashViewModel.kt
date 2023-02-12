package com.msg.gcms.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.auth.GetRefreshExpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getRefreshExpUseCase: GetRefreshExpUseCase
) : ViewModel() {
    private val currentTime = LocalDateTime.now()

    private val _isLoginAble = MutableLiveData<Boolean>()
    val isLoginAble: LiveData<Boolean> get() = _isLoginAble

    fun checkAutoLoginAble(context: Context) = viewModelScope.launch {
        getRefreshExpUseCase().onSuccess {
            if (it.isBlank()) {
                _isLoginAble.value = false
            } else {
                val refreshExpriedAt = LocalDateTime.parse(
                    it
                )
                _isLoginAble.value = !currentTime.isAfter(refreshExpriedAt)
            }
        }.onFailure {
            Toast.makeText(context, "알 수 없는 오류가 발생했습니다", Toast.LENGTH_SHORT).show()
        }
    }
}