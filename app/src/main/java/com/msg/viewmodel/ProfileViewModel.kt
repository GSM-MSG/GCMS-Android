package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.domain.usecase.img.ImageUseCase
import com.msg.gcms.domain.usecase.user.EditProfileUseCase
import com.msg.gcms.domain.usecase.common.LogoutUseCase
import com.msg.gcms.domain.usecase.user.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
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

    private val _profileImg = MutableLiveData<String>()
    val profileImg: LiveData<String> get() = _profileImg
    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val response = profileUseCase.getUserInfo()
                when (response.code()) {
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
                val response = logoutUseCase.postLogout()
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
                val response = imgUseCase.postImg(listOf(img))
                when (response.code()) {
                    in 200..299 -> {
                        _profileImg.value = response.body()!!.get(0)
                    }
                    else -> Log.d("안ㄴ", "saveImg: ")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveImg() {
        Log.d("안ㄴ", "saveImg: ${_profileImg.value}")
        viewModelScope.launch {
            try {
                val response = editProfileUseCase.putProfile(UserProfileRequest(_profileImg.value!!))
                Log.d("안ㄴ", "saveImg: ${response.code()}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}