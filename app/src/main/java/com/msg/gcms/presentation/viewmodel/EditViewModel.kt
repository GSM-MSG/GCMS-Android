package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData
import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.club.EditClubInfoUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.base.LottieFragment
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase,
    private val getSearchUserUseCase: GetSearchUserUseCase,
    private val imageUseCase: ImageUseCase,
    private val editClubInfoUseCase: EditClubInfoUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {

    private val _clubInfo = MutableLiveData<ClubDetailData>()
    val clubInfo: LiveData<ClubDetailData> get() = _clubInfo

    private val _result = MutableLiveData<List<GetSearchUserData>>()
    val result: LiveData<List<GetSearchUserData>> get() = _result

    private val _convertBannerImage = MutableLiveData<List<String>>()
    val convertBannerImage: LiveData<List<String>> get() = _convertBannerImage

    private val _convertActivityPhoto = MutableLiveData<List<String>>()
    val convertActivityPhoto: LiveData<List<String>> get() = _convertActivityPhoto

    private val _editClubResult = MutableLiveData<Event>()
    val editClubResult: LiveData<Event> get() = _editClubResult

    private val lottie by lazy { LottieFragment() }

    private val _addedMemberList = mutableListOf<AddMemberType>()

    private val _addedMemberData = MutableLiveData<List<AddMemberType>>()
    val addedMemberData: LiveData<List<AddMemberType>> get() = _addedMemberData

    fun getClubInfo(clubId: Long) {
        viewModelScope.launch {
            getDetailUseCase(
                clubId = clubId
            ).onSuccess {
                memberCheck(it.member)
                _clubInfo.value = it
                Log.d("TAG", "getClubInfo: $it")
            }.onFailure {
                when (it) {
                    is UnauthorizedException, is NeedLoginException -> {
                        saveTokenInfoUseCase()
                        Log.d("TAG", "getClubInfo: $it")
                    }
                    is NotFoundException -> Log.d("TAG", "getClubInfo: $it")
                    else -> Log.d("TAG", "getClubInfo: $it")
                }
            }
        }
    }

    private fun memberCheck(list: List<ClubMemberData>) {
        _addedMemberList.addAll(list.map {
            AddMemberType(
                uuid = it.uuid,
                userName = it.name,
                userImg = it.userImg
            )
        })
        _addedMemberData.value = _addedMemberList
    }

    fun getSearchUser(name: String, type: String) {
        val queryString: HashMap<String, String> = HashMap()
        queryString["name"] = name
        queryString["type"] = type
        viewModelScope.launch {
            getSearchUserUseCase(
                queryString
            ).onSuccess {
                _result.value = it
                Log.d("TAG", "searchResult: ${_result.value}")
            }.onFailure {
                when (it) {
                    is UnauthorizedException, is NeedLoginException -> {
                        saveTokenInfoUseCase()
                        Log.d("TAG", "getSearchUser: $it")
                    }
                    is ServerException -> Log.d("TAG", "getSearchUser: $it")
                    else -> Log.d("TAG", "getSearchUser: $it")
                }
            }
        }
    }

    fun changeMemList(memberList: List<AddMemberType>) {
        _addedMemberData.value = memberList.ifEmpty {
            listOf(
                AddMemberType(
                    uuid = null,
                    userName = "추가하기",
                    userImg = null
                )
            )
        }
    }

    fun uploadActivityPhoto(list: List<MultipartBody.Part>) {
        viewModelScope.launch {
            imageUseCase(
                image = list
            ).onSuccess {
                _convertActivityPhoto.value = it.images
            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d("TAG", "uploadImage: $it")
                    else -> {
                        Log.e("TAG", "uploadImage: $it")
                    }
                }
            }
        }
    }

    fun uploadBannerImage(list: List<MultipartBody.Part>) {
        Log.d("TAG", "uploadImage")
        viewModelScope.launch {
            imageUseCase(
                image = list
            ).onSuccess {
                Log.d("TAG", "uploadImage: $it")
                // newPhotos = it.images.toMutableList()
                _convertBannerImage.value = it.images

            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d("TAG", "uploadImage: $it")
                    else -> {
                        Log.e("TAG", "uploadImage: $it")
                    }
                }
            }
        }
    }

    fun startLottie(fragmentManager: FragmentManager) {
        if (!lottie.isAdded) {
            lottie.show(fragmentManager, "Lottie")
        }
    }

    fun stopLottie() {
        if (lottie.isAdded) {
            lottie.dismissAllowingStateLoss()
        }
    }

    fun putChangeClubInfo(body: ModifyClubInfoData, clubId: Long) {
        viewModelScope.launch {
            editClubInfoUseCase(
                body = body,
                clubId = clubId
            ).onSuccess {
                _editClubResult.value = Event.Success
            }.onFailure {
                _editClubResult.value = when (it) {
                    is BadRequestException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.BadRequest
                    }
                    is UnauthorizedException, is NeedLoginException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        saveTokenInfoUseCase()
                        Event.Unauthorized
                    }
                    is ForBiddenException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.ForBidden
                    }
                    is NotFoundException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.NotFound
                    }
                    is ConflictException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.Conflict
                    }
                    is ServerException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }
}