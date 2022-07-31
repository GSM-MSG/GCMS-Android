package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase,
    private val userUserCase: UserUseCase
    ) : ViewModel() {

    private val _clubInfo = MutableLiveData<ClubInfoResponse>()
    val clubInfo: LiveData<ClubInfoResponse> get() = _clubInfo

    private val _clubType = MutableLiveData<String>()
    val clubType: LiveData<String> get() = _clubType

    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String> get() = _clubName

    private val _result = MutableLiveData<List<UserData>>()
    val result: LiveData<List<UserData>> get() = _result

    var beforeActivityPhotoList = mutableListOf<String>()
    var afterActivityPhotoList = mutableListOf<ActivityPhotoType>()

    private var clubMemberEmail = mutableListOf<String>()

    var memberList: MutableList<UserData> = mutableListOf()

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
                val response = getDetailUseCase.getDetail(
                    type = _clubType.value.toString(),
                    clubName = _clubName.value.toString()
                )
                _clubInfo.value = response.body()
                when (response.code()) {
                    200 -> {
                        Log.d("TAG", "getClubInfo: ${response.body()}")
                        memberCheck()
                        beforeActivityPhotoList = clubInfo.value!!.activityUrls.toMutableList()
                    }
                    else -> {
                        Log.d("TAG", "getClubInfo: ${response.code()}, ${response.body()}")
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
        }
        else {
            memberList.addAll(clubInfo.value!!.member)
            Log.d("TAG", "memberCheck: $memberList")
        }
    }

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
        memberList.distinct()
    }
}