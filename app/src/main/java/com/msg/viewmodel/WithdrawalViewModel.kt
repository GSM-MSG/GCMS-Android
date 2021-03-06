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

    private val _isWithdrawal = MutableLiveData<Boolean>()
    val isWithdrawal: LiveData<Boolean> get() = _isWithdrawal

    fun changeIsApproved(isCheck: Boolean) {
        _isApproved.value = isCheck
    }

    fun withdrawal() {
        viewModelScope.launch {
            try {
                val response = useCase.deleteUser()
                when(response.code()) {
                    in 200..299 -> _isWithdrawal.value = true
                    else -> _isWithdrawal.value = false
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}