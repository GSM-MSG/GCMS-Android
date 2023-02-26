package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.user.DeleteUserUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {

    private val _isApproved = MutableLiveData<Boolean>()
    val isApproved: LiveData<Boolean> get() = _isApproved

    private val _withDrawalRequest = MutableLiveData<Event>()
    val withDrawalRequest: LiveData<Event> get() = _withDrawalRequest

    fun changeIsApproved(isCheck: Boolean) {
        _isApproved.value = isCheck
    }

    fun withdrawal() = viewModelScope.launch {
        deleteUserUseCase()
            .onSuccess {
                _withDrawalRequest.value = Event.Success
                saveTokenInfoUseCase()
            }.onFailure {
                _withDrawalRequest.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
    }
}