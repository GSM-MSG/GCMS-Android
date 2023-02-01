package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.domain.usecase.user.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _isApproved = MutableLiveData<Boolean>()
    val isApproved: LiveData<Boolean> get() = _isApproved

    private val _isWithdrawal = MutableLiveData<Boolean>()
    val isWithdrawal: LiveData<Boolean> get() = _isWithdrawal

    fun changeIsApproved(isCheck: Boolean) {
        _isApproved.value = isCheck
    }

    fun withdrawal() {

    }
}