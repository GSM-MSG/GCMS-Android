package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.PostCreateClubUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import com.msg.gcms.presentation.adapter.activity_photo.ActivityPhotoType
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.viewmodel.util.Event
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

    private val _searchUserResult = MutableLiveData<List<GetSearchUserData>>()
    val searchUserResult: LiveData<List<GetSearchUserData>> get() = _searchUserResult

    private val _searchUserState = MutableLiveData<Event>()
    val searchUserState: LiveData<Event> get() = _searchUserState

    private val _bannerResult = MutableLiveData<String>()
    val bannerResult: LiveData<String> get() = _bannerResult

    private val _activityPhotoResult = MutableLiveData<List<String>>()
    val activityPhoto: LiveData<List<String>> get() = _activityPhotoResult

    var memberList: MutableList<AddMemberType> = mutableListOf()

    private var clubMemberEmail = mutableListOf<String>()

    private val _imageUploadCheck = MutableLiveData<Boolean?>()
    val imageUploadCheck: LiveData<Boolean?> get() = _imageUploadCheck

    private var _bannerUpload: Boolean? = null

    private var _activityUpload: Boolean? = null

    var activityPhotoList = mutableListOf<ActivityPhotoType>()

    private val _createClubResult = MutableLiveData<Event>()
    val createClubResult: LiveData<Event> get() = _createClubResult

    // private val _status = MutableLiveData<Int>()
    // val status: LiveData<Int> get() = _status

    private var isAlreadyRequest = false

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
                _searchUserResult.value = it
                _searchUserState.value = Event.Success
                Log.d("TAG", "searchResult: ${_searchUserResult.value}")
            }.onFailure {
                _searchUserState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "searchResult: $it ")
                        Event.Unauthorized
                    }
                    is ServerException -> {
                        Log.d("TAG", "getSearchUser: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "searchResult: $it ")
                        Event.UnKnown
                    }
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

    fun bannerImageUpload(image: List<MultipartBody.Part>) {
        viewModelScope.launch {
            imageUseCase(
                image = image
            ).onSuccess {
                Log.d("TAG", "banner: $it")
                _bannerResult.value = it.images[0]
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
                Log.d("TAG", "activityPhoto: $it")
                _activityPhotoResult.value = it.images
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
            if(!isAlreadyRequest) {
                isAlreadyRequest = true
                if (_activityPhotoResult.value == null)
                    _activityPhotoResult.value = emptyList()
                Log.d(
                    "TAG",
                    "createClub: type: ${clubType.value.toString()}, title: $title, description: $description, contact: $contact, notionLink: $notionLink, teacher: $teacher, member: $clubMemberEmail, activityUrls: ${activityPhoto.value}, bannerUrl: ${bannerResult.value}"
                )
                clubMemberEmail.remove("")
                postCreateClubUseCase(
                    CreateClubData
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
                    _createClubResult.value = Event.Success

                }.onFailure {
                    _createClubResult.value = when(it) {
                        is BadRequestException -> {
                            Event.BadRequest
                        }
                        is UnauthorizedException -> {
                            Event.Unauthorized
                        }
                        is ConflictException -> {
                            Event.Conflict
                        }
                        is ServerException -> {
                            Event.Server
                        }
                        else -> {
                            Event.UnKnown
                        }
                    }
                    Log.d("TAG", "createClub: $it")
                }
            }
        }
    }
}
