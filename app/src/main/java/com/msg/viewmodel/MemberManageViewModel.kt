package com.msg.viewmodel

import android.util.Log
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
): ViewModel() {

    private val _memberList = MutableLiveData<List<MemberSummaryResponse>>()
    val memberList: LiveData<List<MemberSummaryResponse>> get() = _memberList

    private val _applicantList = MutableLiveData<List<MemberSummaryResponse>>()
    val applicantList: LiveData<List<MemberSummaryResponse>> get() = _applicantList
    fun getMember() {
        viewModelScope.launch {
            try {
                val response = getMemberUseCase.getMember("kke", "MAJOR")
                _memberList.value = response.body()!!.requestUser
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getApplicant() {
        viewModelScope.launch {
            try{
                val response = getApplicantUseCase.getApplicant("kke", "MAJOR")
                _applicantList.value = response.body()!!.requestUser
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun kickUser(id: String) {
        viewModelScope.launch {
            try {
                val response = userKickUseCase.kick(MemberManagementRequest("kke", "MAJOR", id))
                Log.d("안ㄴ", "ickUser: ${response.code()}")
            } catch (e: Exception) {
                Log.d("안ㄴ", "kickUser:E ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun delegate(id: String) {
        viewModelScope.launch {
            try {
                val response = userDelegateUseCase.mandate(MemberManagementRequest("kke", "MAJOR", id))
                Log.d("안ㄴ", "delegate: ${response.code()}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun reject(id: String) {
        viewModelScope.launch {
            try {
                val response =
                    applicantRejectUseCase.reject(MemberManagementRequest("kke", "MAJOR", id))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun accept(id: String) {
        viewModelScope.launch {
            try {
                val response = applicantAcceptUseCase.accept(MemberManagementRequest("kke", "MAJOR", id))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}