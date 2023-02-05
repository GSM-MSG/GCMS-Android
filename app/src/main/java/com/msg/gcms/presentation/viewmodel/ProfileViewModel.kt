package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserInfoResponse
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.auth.LogoutUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.EditProfileUseCase
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
) : ViewModel() {
    private val _clubStatus = MutableLiveData<Boolean>()
    val clubStatus: LiveData<Boolean> get() = _clubStatus

    private val _afterSchoolStatus = MutableLiveData<Boolean>()
    val afterSchoolStatus: LiveData<Boolean> get() = _afterSchoolStatus

    private val _profileData = MutableLiveData<UserInfoResponse>()
    val profileData: LiveData<UserInfoResponse> get() = _profileData

    fun getUserInfo() {
        viewModelScope.launch {
            profileUseCase().onSuccess {
                _profileData.value = it
                _clubStatus.value = it.clubs.isNotEmpty()
                _afterSchoolStatus.value = it.afterSchools.isNotEmpty()
            }.onFailure {
                _clubStatus.value = false
                when (it) {
                    is UnauthorizedException -> Log.d("TAG", "getUserInfo: $it")
                    is NotFoundException -> Log.d("TAG", "getUserInfo: $it")
                    else -> Log.d("TAG", "getUserInfo: $it")
                }
            }
        }
    }

    fun logout() {
        GCMSApplication.prefs.accessToken = ""
        GCMSApplication.prefs.refreshToken = ""
        GCMSApplication.prefs.accessExp = ""
        GCMSApplication.prefs.refreshExp = ""
    }

    fun uploadImg(img: MultipartBody.Part) {
        viewModelScope.launch {
            imgUseCase(
                image = listOf(img)
            ).onSuccess {
                saveImg(it[0])
            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d("TAG", "uploadImg: $it")
                    else -> Log.d("TAG", "uploadImg: $it")
                }
            }
        }
    }

    fun saveImg(img: String) {
        viewModelScope.launch {
            val response = editProfileUseCase(UserProfileRequest(img))
        }
    }
}