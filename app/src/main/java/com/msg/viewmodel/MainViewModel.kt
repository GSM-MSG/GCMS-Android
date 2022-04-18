package com.msg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import com.msg.gcms.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {
    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String>
        get() = _clubName

    fun setClubName(position: Int){
        when(position){
            0 -> _clubName.value = "전공동아리"
            1 -> _clubName.value = "자율동아리"
            2 -> _clubName.value = "사설동아리"
        }
    }


}