package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.user.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val useCase: DeleteUserUseCase
): ViewModel() {

    private val _isApproved = MutableLiveData<Boolean>()
    val isApproved: LiveData<Boolean> get() = _isApproved

    fun changeIsApproved(isCheck: Boolean) {
        _isApproved.value = isCheck
    }

    fun withdrawal() {
        viewModelScope.launch {
            try {
                val response = useCase.deleteUser()
                when(response.code()) {
                    204 -> Log.d("안ㄴ", "withdrawal: 탈퇴성공")
                    401 -> Log.d("안ㄴ", "withdrawal: 토큰 만료")
                    403 -> Log.d("안ㄴ", "withdrawal: 유저 없음")
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}