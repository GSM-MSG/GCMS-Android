package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NotAcceptableException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.ApplicantAcceptUseCase
import com.msg.gcms.domain.usecase.club.ApplicantRejectUseCase
import com.msg.gcms.domain.usecase.club.GetApplicantUseCase
import com.msg.gcms.domain.usecase.club.GetMemberUseCase
import com.msg.gcms.domain.usecase.club.MandateUseCase
import com.msg.gcms.domain.usecase.club.UserKickUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberManageViewModel @Inject constructor(
    private val getMemberUseCase: GetMemberUseCase,
    private val getApplicantUseCase: GetApplicantUseCase,
    private val userKickUseCase: UserKickUseCase,
    private val userDelegateUseCase: MandateUseCase,
    private val applicantRejectUseCase: ApplicantRejectUseCase,
    private val applicantAcceptUseCase: ApplicantAcceptUseCase
) : ViewModel() {

    private val _memberList = MutableLiveData<List<MemberSummaryResponse>>()
    val memberList: LiveData<List<MemberSummaryResponse>> get() = _memberList

    private val _applicantList = MutableLiveData<List<MemberSummaryResponse>>()
    val applicantList: LiveData<List<MemberSummaryResponse>> get() = _applicantList

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> get() = _status

    private val _clubName = MutableLiveData<String>()
    private val _clubType = MutableLiveData<String>()

    fun setClub(name: String, type: String) {
        _clubName.value = name
        _clubType.value = type
    }

    fun getMember() {
        viewModelScope.launch {
            try {
                getMemberUseCase(
                    clubName = _clubName.value!!,
                    type = _clubType.value!!
                ).onSuccess {
                    _memberList.value = it.body()!!.requestUser
                    _status.value = it.code()
                }.onFailure {
                    when (it) {
                        is ForBiddenException -> Log.d("TAG", "getMember: $it")
                        is NotAcceptableException -> Log.d("TAG", "getMember: $it")
                        else -> Log.d("TAG", "getMember: $it")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getApplicant() {
        viewModelScope.launch {
            try {
                getApplicantUseCase(
                    clubName = _clubName.value!!,
                    type = _clubType.value!!
                ).onSuccess {
                    _applicantList.value = it.body()!!.requestUser
                    _status.value = it.code()
                }.onFailure {
                    when (it) {
                        is UnauthorizedException -> Log.d("TAG", "getApplicant: $it")
                        is NotFoundException -> Log.d("TAG", "getApplicant: $it")
                        is NotAcceptableException -> Log.d("TAG", "getApplicant: $it")
                        else -> Log.d("TAG", "getApplicant: $it")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun kickUser(id: String) {
        viewModelScope.launch {
            try {
                userKickUseCase(
                    MemberManagementRequest(
                        _clubName.value!!,
                        _clubType.value!!,
                        id
                    )
                ).onSuccess {
                    // Todo(Leehyeonbin) 여기도 스테이터스에서 처리함
                    _status.value = it.code()
                }.onFailure {
                    when (it) {
                        is UnauthorizedException -> Log.d("TAG", "kickUser: $it")
                        is ForBiddenException -> Log.d("TAG", "kickUser: $it")
                        else -> Log.d("TAG", "kickUser: $it")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delegate(id: String) {
        viewModelScope.launch {
            try {
                userDelegateUseCase(
                    MemberManagementRequest(
                        _clubName.value!!,
                        _clubType.value!!,
                        id
                    )
                ).onSuccess {
                    // Todo(Leehyeonbin) 여기도 Status로 되어있음
                    _status.value = it.code()
                }.onFailure {
                    when (it) {
                        is UnauthorizedException -> Log.d("TAG", "delegate: $it")
                        is ForBiddenException -> Log.d("TAG", "delegate: $it")
                        is NotFoundException -> Log.d("TAG", "delegate: $it")
                        else -> Log.d("TAG", "delegate: $it")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun reject(id: String) {
        viewModelScope.launch {
            try {
                val response =
                    applicantRejectUseCase(
                        MemberManagementRequest(
                            _clubName.value!!,
                            _clubType.value!!,
                            id
                        )
                    )
                _status.value = response.code()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun accept(id: String) {
        viewModelScope.launch {
            try {
                val response = applicantAcceptUseCase(
                    MemberManagementRequest(
                        _clubName.value!!,
                        _clubType.value!!,
                        id
                    )
                )
                _status.value = response.code()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}