package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubMemberInfo
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.EditClubInfoUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
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
    private val editClubInfoUseCase: EditClubInfoUseCase
) : ViewModel() {

    private val _clubInfo = MutableLiveData<ClubDetailResponse>()
    val clubInfo: LiveData<ClubDetailResponse> get() = _clubInfo

    private val _clubId = MutableLiveData<Long>()
    val clubId: LiveData<Long> get() = _clubId

    private val _result = MutableLiveData<List<UserData>>()
    val result: LiveData<List<UserData>> get() = _result

    var newPhotos = mutableListOf<String>()

    private var clubMemberEmail = mutableListOf<String>()

    var memberList: MutableList<ClubMemberInfo> = mutableListOf()

    private val _convertImage = MutableLiveData<List<String>>()
    val convertImage: LiveData<List<String>> get() = _convertImage

    private val _editClubResult = MutableLiveData<Event>()
    val editClubResult: LiveData<Event> get() = _editClubResult

    private val lottie by lazy { LottieFragment() }

    fun getClubInfo() {
        viewModelScope.launch {
            getDetailUseCase(
                clubId = clubId.value!!
            ).onSuccess {
                _clubInfo.value = it
                Log.d("TAG", "getClubInfo: $it")
                memberCheck()

            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d("TAG", "getClubInfo: $it")
                    is NotFoundException -> Log.d("TAG", "getClubInfo: $it")
                    else -> Log.d("TAG", "getClubInfo: $it")
                }
            }
        }
    }

    private fun memberCheck() {
        if (clubInfo.value!!.member.isEmpty()) {
            memberList.add(
                ClubMemberInfo(
                    uuid = "0",
                    email = "",
                    name = "추가하기",
                    grade = 0,
                    `class` = 0,
                    num = 0,
                    userImg = R.drawable.bg_banner_placeholder.toString()
                )
            )
        } else {
            memberList.addAll(clubInfo.value!!.member)
            Log.d("TAG", "memberCheck: ${memberList.distinct()}")
        }
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
                    is UnauthorizedException -> Log.d("TAG", "getSearchUser: $it")
                    is ServerException -> Log.d("TAG", "getSearchUser: $it")
                    else -> Log.d("TAG", "getSearchUser: $it")
                }
            }
        }
    }

    fun setMemberEmail() {
        memberList.forEach {
            Log.d("TAG", "setMemberEmail: ${it.email}")
            clubMemberEmail.add(it.email)
        }
        memberList.distinct()
    }

    fun uploadImage(list: List<MultipartBody.Part>) {
        Log.d("TAG", "uploadImage")
        viewModelScope.launch {
            imageUseCase(
                list
            ).onSuccess {
                Log.d("TAG", "uploadImage: $it")
                newPhotos = it.toMutableList()
                _convertImage.value = it

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

    fun putChangeClubInfo(body: ModifyClubInfoRequest) {
        viewModelScope.launch {
            editClubInfoUseCase(
                body = body,
                clubId = clubId.value!!
            ).onSuccess {
                _editClubResult.value = Event.Success
            }.onFailure {
                _editClubResult.value = when (it) {
                    is BadRequestException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
                        Event.BadRequest
                    }
                    is UnauthorizedException -> {
                        Log.d("TAG", "putChangeClubInfo: $it")
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