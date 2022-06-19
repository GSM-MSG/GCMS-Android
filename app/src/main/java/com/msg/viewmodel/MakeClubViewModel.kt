package com.msg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.domain.usecase.club.ClubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MakeClubViewModel @Inject constructor(
    useCase: ClubUseCase
) : ViewModel() {

    private var _clubType = MutableLiveData<String>()
    val clubType: LiveData<String> get() = _clubType

    fun clubTypeChange(type: String) {
        _clubType.value = type
    }
}
