package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.user.EditProfileUseCase
import com.msg.gcms.domain.usecase.common.LogoutUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val imgUseCase: ImageUseCase,
    private val editProfileUseCase: EditProfileUseCase
): ViewModel() {
    private val _clubStatus = MutableLiveData<Boolean>()
    val clubStatus: LiveData<Boolean> get() = _clubStatus

    private val _afterSchoolStatus = MutableLiveData<Boolean>()
    val afterSchoolStatus: LiveData<Boolean> get() = _afterSchoolStatus

    private val _profileData = MutableLiveData<UserInfoResponse>()
    val profileData: LiveData<UserInfoResponse> get() = _profileData

    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus: LiveData<Boolean> get() = _logoutStatus

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                profileUseCase().onSuccess {
                    _profileData.value = it.body()
                    _clubStatus.value = it.body()?.clubs?.size != 0
                    _afterSchoolStatus.value = it.body()?.afterSchools?.size != 0
                }.onFailure {
                    _clubStatus.value = false
                    when(it) {
                        is UnauthorizedException -> Log.d("TAG", "getUserInfo: $it")
                        is NotFoundException -> Log.d("TAG", "getUserInfo: $it")
                        else -> Log.d("TAG", "getUserInfo: $it")
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
                val response = logoutUseCase()
                when (response.code()) {
                    200 -> {
                        _logoutStatus.value = true
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "logout: ${e.message}")
            }
        }
    }

    fun uploadImg(img: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = imgUseCase(listOf(img))
                when (response.code()) {
                    in 200..299 -> {
                        saveImg(response.body()!!.get(0))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveImg(img: String) {
        viewModelScope.launch {
            try {
                val response = editProfileUseCase(UserProfileRequest(img))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}