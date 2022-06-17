package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
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

    private val _afterSchoolStatus = MutableLiveData<Boolean>()
    val afterSchoolStatus: LiveData<Boolean> get() = _afterSchoolStatus

    private val _profileData = MutableLiveData<UserInfoResponse>()
    val profileData: LiveData<UserInfoResponse> get() = _profileData

    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus: LiveData<Boolean> get() = _logoutStatus
    fun getUserInfo(){
        viewModelScope.launch {
            try{
                val response = profileUseCase.getUserInfo()
                when(response.code()){
                    200 -> {
                        _profileData.value = response.body()
                        _clubStatus.value = response.body()?.clubs?.size != 0
                        _afterSchoolStatus.value = response.body()?.afters?.size != 0
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

    fun logout(){
        viewModelScope.launch {
            try {
                val response = profileUseCase.postLogout()
                when(response.code()){
                    200 -> {
                        _logoutStatus.value = true
                    }
                }
            } catch (e: Exception){
                Log.d("TAG", "logout: ${e.message}")
            }
        }
    }
}