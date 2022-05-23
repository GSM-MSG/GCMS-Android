package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.usecase.user.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
): ViewModel() {
    private val _clubStatus = MutableLiveData<Boolean>()
    val clubStatus: LiveData<Boolean> get() = _clubStatus

    fun getUserInfo(){
        viewModelScope.launch {
            try{
                val response = profileUseCase.getUserInfo()
                when(response.code()){
                    200 -> {
                        _clubStatus.value = response.body()?.clubs?.size != 0
                    }
                    else -> {
                        _clubStatus.value = false
                    }
                }
            } catch (e: Exception){
                Log.d("ERROR", "getUserInfo: ${e.message}")
            }
        }
    }
}