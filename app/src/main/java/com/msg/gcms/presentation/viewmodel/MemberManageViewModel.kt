package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberSummaryResponse
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NotAcceptableException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.ApplicantAcceptUseCase
import com.msg.gcms.domain.usecase.club.ApplicantRejectUseCase
import com.msg.gcms.domain.usecase.club.GetApplicantUseCase
import com.msg.gcms.domain.usecase.club.GetMemberUseCase
import com.msg.gcms.domain.usecase.club.MandateUseCase
import com.msg.gcms.domain.usecase.club.UserKickUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
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

    private val _getMemberListState = MutableLiveData<Event>()
    val getMemberListState: LiveData<Event> get() = _getMemberListState

    private val _getApplicantListState = MutableLiveData<Event>()
    val getApplicantListState: LiveData<Event> get() = _getApplicantListState

    private val _kickUserState = MutableLiveData<Event>()
    val kickUserState: LiveData<Event> get() = _kickUserState

    private val _delegateState = MutableLiveData<Event>()
    val delegateState: LiveData<Event> get() = _delegateState

    private val _acceptApplicantState = MutableLiveData<Event>()
    val acceptApplicantState: LiveData<Event> get() = _acceptApplicantState

    private val _rejectApplicantState = MutableLiveData<Event>()
    val rejectApplicantState: LiveData<Event> get() = _rejectApplicantState

    fun setClub(name: String, type: String) {
        _clubName.value = name
        _clubType.value = type
    }

    fun getMember() {
        viewModelScope.launch {
            getMemberUseCase(
                clubName = _clubName.value!!,
                type = _clubType.value!!
            ).onSuccess {
                //Todo(Leeyeonbin) 여기 코드 다 수정하기
                _memberList.value = it.requestUser
                _getMemberListState.value = Event.Success
                // _status.value = it.code()
            }.onFailure {
                _getMemberListState.value = when (it) {
                    is ForBiddenException -> {
                        Log.d("TAG", "getMember: $it")
                        Event.ForBidden
                    }
                    is NotAcceptableException -> {
                        Log.d("TAG", "getMember: $it")
                        Event.NotAcceptable
                    }
                    is ServerException -> {
                        Log.d("TAG", "getMember: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "getMember: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun getApplicant() {
        viewModelScope.launch {
            getApplicantUseCase(
                clubName = _clubName.value!!,
                type = _clubType.value!!
            ).onSuccess {
                //Todo(Leeyeonbin) 여기 코드 다 수정하기
                _applicantList.value = it.requestUser
                _getApplicantListState.value = Event.Success
            }.onFailure {
                _getApplicantListState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "getApplicant: $it")
                        Event.Unauthorized
                    }
                    is NotFoundException -> {
                        Log.d("TAG", "getApplicant: $it")
                        Event.NotFound
                    }
                    is NotAcceptableException -> {
                        Log.d("TAG", "getApplicant: $it")
                        Event.NotAcceptable
                    }
                    is ServerException -> {
                        Log.d("TAG", "getApplicant: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "getApplicant: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun kickUser(id: String) {
        viewModelScope.launch {
            userKickUseCase(
                MemberManagementRequest(
                    _clubName.value!!,
                    _clubType.value!!,
                    id
                )
            ).onSuccess {
                // Todo(Leehyeonbin) 여기도 스테이터스에서 처리함
                // _status.value = it.code()
                _kickUserState.value = Event.Success
            }.onFailure {
                _kickUserState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "kickUser: $it")
                        Event.Unauthorized

                    }
                    is ForBiddenException -> {
                        Log.d("TAG", "kickUser: $it")
                        Event.ForBidden
                    }
                    is ServerException -> {
                        Log.d("TAG", "kickUser: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "kickUser: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun delegate(id: String) {
        viewModelScope.launch {
            userDelegateUseCase(
                MemberManagementRequest(
                    _clubName.value!!,
                    _clubType.value!!,
                    id
                )
            ).onSuccess {
                // Todo(Leehyeonbin) 여기도 Status로 되어있음
                // _status.value = it.code()
                _delegateState.value = Event.Success
            }.onFailure {
                _delegateState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Unauthorized
                    }
                    is ForBiddenException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.ForBidden
                    }
                    is NotFoundException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.NotFound
                    }
                    is ServerException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "delegate: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun accept(id: String) {
        viewModelScope.launch {
            applicantAcceptUseCase(
                MemberManagementRequest(
                    _clubName.value!!,
                    _clubType.value!!,
                    id
                )
            ).onSuccess {
                // Todo (LeeHyeonbin) 여기도
                // _status.value = it.code()
                _acceptApplicantState.value = Event.Success
            }.onFailure {
                _acceptApplicantState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Unauthorized
                    }
                    is ForBiddenException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.ForBidden
                    }
                    is NotFoundException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.NotFound
                    }
                    is ServerException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "delegate: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun reject(id: String) {
        viewModelScope.launch {
            applicantRejectUseCase(
                MemberManagementRequest(
                    _clubName.value!!,
                    _clubType.value!!,
                    id
                )
            ).onSuccess {
                // Todo(LeeHyeonbin) 여기도 Status로 되어있리
                // _status.value = it.code()
                _rejectApplicantState.value = Event.Success
            }.onFailure {
                _rejectApplicantState.value = when (it) {
                    is UnauthorizedException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Unauthorized
                    }
                    is ForBiddenException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.ForBidden
                    }
                    is NotFoundException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.NotFound
                    }
                    is ServerException -> {
                        Log.d("TAG", "delegate: $it")
                        Event.Server
                    }
                    else -> {
                        Log.d("TAG", "delegate: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }
}