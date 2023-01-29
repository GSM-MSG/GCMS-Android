package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.PostCreateClubUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MakeClubViewModel @Inject constructor(
    private val postCreateClubUseCase: PostCreateClubUseCase,
    private val getSearchUserUseCase: GetSearchUserUseCase,
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

    private val _imageUploadCheck = MutableLiveData<Boolean?>()
    val imageUploadCheck: LiveData<Boolean?> get() = _imageUploadCheck

    private var _bannerUpload: Boolean? = null

    private var _activityUpload: Boolean? = null

    var activityPhotoList = mutableListOf<ActivityPhotoType>()

    private val _createResult = MutableLiveData<Boolean>()
    val createResult: LiveData<Boolean> get() = _createResult

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> get() = _status

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
            getSearchUserUseCase(
                queryString
            ).onSuccess {
                _result.value = it.body()
                Log.d("TAG", "searchResult: ${_result.value}")
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d("TAG", "searchResult: $it ")
                    else ->
                        Log.d("TAG", "searchResult: $it ")
                }
            }
        }
    }

    fun imageUploadCheck() {
        if (_bannerUpload == true && _activityUpload == true) {
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
            imageUseCase(
                image = image
            ).onSuccess {
                Log.d("TAG", "banner: ${it.body()}")
                _bannerResult.value = it.body()?.get(0)
                _bannerUpload = true
                imageUploadCheck()
            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d("TAG", "changeImage: else $it")
                    else -> Log.d("TAG", "changeImage: else $it")
                }
            }
        }
    }

    fun activityPhotoUpload(image: List<MultipartBody.Part>) {
        viewModelScope.launch {
            imageUseCase(
                image = image
            ).onSuccess {
                Log.d("TAG", "activityPhoto: ${it.body()}")
                _activityPhotoResult.value = it.body()
                _activityUpload = true
                imageUploadCheck()
            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d("TAG", "changeImage: else $it")
                    else -> Log.d("TAG", "changeImage: else $it")
                }
            }
        }
    }

    fun createClub() {
        viewModelScope.launch {
            if (_activityPhotoResult.value == null)
                _activityPhotoResult.value = emptyList()

            Log.d(
                "TAG",
                "createClub: type: ${clubType.value.toString()}, title: $title, description: $description, contact: $contact, notionLink: $notionLink, teacher: $teacher, member: $clubMemberEmail, activityUrls: ${activityPhoto.value}, bannerUrl: ${bannerResult.value}"
            )
            clubMemberEmail.remove("")
            postCreateClubUseCase(
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
            ).onSuccess {
                Log.d("TAG", "createClub: 성공")
                _status.value = it.code()
                _createResult.value = true

            }.onFailure {
                // Todo(LeeHyeonbin) 스테이터스 코드로 예외되는거 수정하기
                // _status.value = it.code()
                _createResult.value = false
                Log.d("TAG", "createClub: $it")
            }
        }
    }
}
