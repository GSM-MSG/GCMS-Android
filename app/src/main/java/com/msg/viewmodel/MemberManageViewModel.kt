package com.msg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
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
                val response = getMemberUseCase.getMember(_clubName.value!!, _clubType.value!!)
                _memberList.value = response.body()!!.requestUser
                _status.value = response.code()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getApplicant() {
        viewModelScope.launch {
            try {
                val response =
                    getApplicantUseCase.getApplicant(_clubName.value!!, _clubType.value!!)
                _applicantList.value = response.body()!!.requestUser
                _status.value = response.code()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun kickUser(id: String) {
        viewModelScope.launch {
            try {
                val response = userKickUseCase.kick(
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

    fun delegate(id: String) {
        viewModelScope.launch {
            try {
                val response = userDelegateUseCase.mandate(
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

    fun reject(id: String) {
        viewModelScope.launch {
            try {
                val response =
                    applicantRejectUseCase.reject(
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
                val response = applicantAcceptUseCase.accept(
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