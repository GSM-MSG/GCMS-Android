package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.usecase.club.ClubUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MakeClubViewModel @Inject constructor(
    private val clubUseCase: ClubUseCase,
    private val userUserCase: UserUseCase,
    private val imageUseCase: ImageUseCase
) : ViewModel() {

    private var _clubType = MutableLiveData("MAJOR")
    val clubType: LiveData<String> get() = _clubType

    fun clubTypeChange(type: String) {
        _clubType.value = type
    }

    private val _result = MutableLiveData<List<UserData>>()
    val result: LiveData<List<UserData>> get() = _result

    private val _bannerResult = MutableLiveData<String>()
    val bannerResult: LiveData<String> get() = _bannerResult

    private val _activityPhotoResult = MutableLiveData<List<String>>()
    val activityPhoto: LiveData<List<String>> get() = _activityPhotoResult

    var memberList: MutableList<UserData> = mutableListOf()

    private var clubMemberEmail = mutableListOf<String>()

    private val _imageUploadCheck = MutableLiveData<Boolean>()
    val imageUploadCheck : LiveData<Boolean> get() = _imageUploadCheck

    private var _bannerUpload = false

    private var _activityUpload = false

    private val _createResult = MutableLiveData<Boolean>()
    val createResult: LiveData<Boolean> get() = _createResult


    var title: String = ""
    var description: String = ""
    var contact: String = ""
    var notionLink: String = ""
    var teacher: String = ""

    fun getSearchUser(name: String) {
        val queryString: HashMap<String, String> = HashMap()
        queryString["name"] = name
        queryString["type"] = clubType.value.toString()
        viewModelScope.launch {
            val response = userUserCase.getSearchUser(queryString)
            when (response.code()) {
                200 -> {
                    _result.value = response.body()
                    Log.d("TAG", "searchResult: ${_result.value}")
                }
                else -> {
                    Log.d("TAG", "searchResult: ${response.body()} ")
                }
            }
        }
    }

    fun imageUploadCheck() {
        if(_bannerUpload && _activityUpload) {
            _imageUploadCheck.value = true
        }
    }

    fun setActivityPhotoUpload() {
        _activityUpload = true
    }

    fun setMemberEmail() {
        memberList.forEach {
            Log.d("TAG", "setMemberEmail: ${it.email}")
            clubMemberEmail.add(it.email)
        }
    }

    fun bannerImageUpload(image: List<MultipartBody.Part>) {
        viewModelScope.launch {
            val response = imageUseCase.postImage(image)
            when (response.code()) {
                201 -> {
                    Log.d("TAG", "banner: ${response.body()}")
                    _bannerResult.value = response.body()?.get(0)
                    _bannerUpload = true
                    imageUploadCheck()
                }
                else -> {
                    Log.d("TAG", "changeImage: else ${response.code()}")
                }
            }
        }
    }

    fun activityPhotoUpload(image: List<MultipartBody.Part>) {
        viewModelScope.launch {
            val response = imageUseCase.postImage(image)
            when (response.code()) {
                201 -> {
                    Log.d("TAG", "activityPhoto: ${response.body()}")
                    _activityPhotoResult.value = response.body()
                    _activityUpload = true
                    imageUploadCheck()
                }
                else -> {
                    Log.d("TAG", "changeImage: else ${response.code()}")
                }
            }
        }
    }

    fun createClub() {
        viewModelScope.launch {
            Log.d(
                "TAG",
                "createClub: type: ${clubType.value.toString()}, title: $title, description: $description, contact: $contact, notionLink: $notionLink, teacher: $teacher, member: $clubMemberEmail, activityUrls: ${activityPhoto.value}, bannerUrl: ${bannerResult.value}"
            )
            clubMemberEmail.remove("")
            val response = clubUseCase.postCreateClub(
                CreateClubRequest
                    (
                    type = clubType.value.toString().trim(),
                    title = title,
                    description = description,
                    contact = contact,
                    notionLink = notionLink,
                    teacher = teacher,
                    member = clubMemberEmail,
                    activityUrls = _activityPhotoResult.value,
                    bannerUrl = _bannerResult.value!!
                )
            )
            when (response.code()) {
                201 -> {
                    Log.d("TAG", "createClub: 성공")
                    _createResult.value = true
                }
                else -> {
                    Log.d("TAG", "createClub: $response, ${response.body()}")
                    _createResult.value = false
                }
            }
        }
    }
}
