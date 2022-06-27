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

    private val _bannerResult = MutableLiveData<List<String>>()
    val bannerResult: LiveData<List<String>> get() = _bannerResult

    private val _activityPhotoResult = MutableLiveData<List<String>>()
    val activityPhoto: LiveData<List<String>> get() = _activityPhotoResult

    var memberList: MutableList<UserData> = mutableListOf()

    private var clubMemberEmail = mutableListOf<String>()

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
                    _bannerResult.value = response.body()
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
                    activityUrls = _activityPhotoResult.value ?: bannerResult.value,
                    bannerUrl = bannerResult.value!!
                )
            )
            when (response.code()) {
                201 -> {
                    Log.d("TAG", "createClub: 성공")
                }
                else -> {
                    Log.d("TAG", "createClub: $response, ${response.body()}")
                }
            }
        }
    }
}
