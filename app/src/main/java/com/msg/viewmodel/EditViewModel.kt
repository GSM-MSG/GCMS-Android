package com.msg.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.datasource.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.EditClubInfoUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import com.msg.gcms.ui.base.LottieFragment
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

    private val _clubInfo = MutableLiveData<ClubInfoResponse>()
    val clubInfo: LiveData<ClubInfoResponse> get() = _clubInfo

    private val _clubType = MutableLiveData<String>()
    val clubType: LiveData<String> get() = _clubType

    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String> get() = _clubName

    private val _result = MutableLiveData<List<UserData>>()
    val result: LiveData<List<UserData>> get() = _result

    var newPhotos = mutableListOf<String>()

    private var clubMemberEmail = mutableListOf<String>()

    var memberList: MutableList<UserData> = mutableListOf()

    private val _convertImage = MutableLiveData<List<String>>()
    val convertImage: LiveData<List<String>> get() = _convertImage

    private val _editClubResult = MutableLiveData<Int>()
    val editClubResult: LiveData<Int> get() = _editClubResult

    private val lottie by lazy { LottieFragment() }

    fun clubTypeDivider(clubType: String) {
        val clubType = clubType.split("+")
        this._clubType.value = clubType[1].trim()
        this._clubName.value = clubType[0].trim()
        Log.d("TAG", "clubTypeDivider: ${this.clubType.value}, ${this.clubName.value}")
    }

    fun getClubInfo() {
        viewModelScope.launch {
            try {
                Log.d(
                    "TAG",
                    "getClubInfo: ${_clubType.value.toString()}, ${_clubName.value.toString()}"
                )
                getDetailUseCase(
                    type = _clubType.value.toString(),
                    clubName = _clubName.value.toString()
                ).onSuccess {
                    _clubInfo.value = it.body()
                    Log.d("TAG", "getClubInfo: ${it.body()}")
                    memberCheck()

                }.onFailure {
                    when (it) {
                        is UnauthorizedException -> Log.d("TAG", "getClubInfo: $it")
                        is NotFoundException -> Log.d("TAG", "getClubInfo: $it")
                        else -> Log.d("TAG", "getClubInfo: $it")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun memberCheck() {
        if (clubInfo.value!!.member.isEmpty()) {
            memberList.add(
                UserData(
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
            try {
                imageUseCase(
                    list
                ).onSuccess {
                    Log.d("TAG", "uploadImage: ${it.body()}")
                    newPhotos = it.body()!!.toMutableList()
                    _convertImage.value = it.body()
                }.onFailure {
                    when (it) {
                        is BadRequestException -> Log.d("TAG", "uploadImage: $it")
                        else -> {
                            Log.e("TAG", "uploadImage: ${it}")
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
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
            try {
                val response = editClubInfoUseCase(body)
                when (response.code()) {
                    200 -> {
                        Log.d("TAG", "putChangeClubInfo: ${response.code()}")
                        _editClubResult.value = response.code()
                    }
                    else -> {
                        Log.d("TAG", "putChangeClubInfo: $response")
                        _editClubResult.value = response.code()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}