package com.msg.gcms.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.applicant.clubApplyAccept.ClubApplyAcceptData
import com.msg.gcms.domain.data.applicant.club_apply_reject.ClubApplyRejectData
import com.msg.gcms.domain.data.applicant.get_applicant_list.ApplicantListData
import com.msg.gcms.domain.data.club_member.delegation_of_manager.DelegationOfManagerData
import com.msg.gcms.domain.data.club_member.get_club_member.MemberData
import com.msg.gcms.domain.data.club_member.member_expelled.MemberExpelledData
import com.msg.gcms.domain.usecase.applicant.ApplicantAcceptUseCase
import com.msg.gcms.domain.usecase.applicant.ApplicantRejectUseCase
import com.msg.gcms.domain.usecase.applicant.GetApplicantUseCase
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.club_member.GetMemberUseCase
import com.msg.gcms.domain.usecase.club_member.MandateUseCase
import com.msg.gcms.domain.usecase.club_member.UserKickUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MemberManageViewModel @Inject constructor(
    private val getMemberUseCase: GetMemberUseCase,
    private val getApplicantUseCase: GetApplicantUseCase,
    private val userKickUseCase: UserKickUseCase,
    private val userDelegateUseCase: MandateUseCase,
    private val applicantRejectUseCase: ApplicantRejectUseCase,
    private val applicantAcceptUseCase: ApplicantAcceptUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {

    private val _memberList = MutableLiveData<List<MemberData>>()
    val memberList: LiveData<List<MemberData>> get() = _memberList

    private val _applicantList = MutableLiveData<List<ApplicantListData>>()
    val applicantList: LiveData<List<ApplicantListData>> get() = _applicantList

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> get() = _status

    // private val _clubName = MutableLiveData<String>()
    // private val _clubType = MutableLiveData<String>()

    private val _clubId = mutableStateOf<Long>(0)

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

    fun setClubId(clubId: Long) {
        _clubId.value = clubId
    }

    fun getMember() {
        viewModelScope.launch {
            getMemberUseCase(
                clubId = _clubId.value
            ).onSuccess {
                _memberList.value =
                    it.requestUser.map { data ->
                        MemberData(
                            uuid = data.uuid,
                            email = data.email,
                            name = data.name,
                            grade = data.grade,
                            `class` = data.`class`,
                            num = data.num,
                            userImg = data.userImg,
                            scope = data.scope
                        )
                    }
                _getMemberListState.value = Event.Success
            }.onFailure {
                _getMemberListState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }

    fun getApplicant() {
        viewModelScope.launch {
            getApplicantUseCase(
                clubId = _clubId.value
            ).onSuccess {
                _applicantList.value = it.applicantList
                _getApplicantListState.value = Event.Success
            }.onFailure {
                _getApplicantListState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }

    fun kickUser(uuid: UUID) {
        viewModelScope.launch {
            userKickUseCase(
                body = MemberExpelledData(uuid),
                clubId = _clubId.value
            ).onSuccess {
                _kickUserState.value = Event.Success
            }.onFailure {
                _kickUserState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }

    fun delegate(id: UUID) {
        viewModelScope.launch {
            userDelegateUseCase(
                clubId = _clubId.value,
                body = DelegationOfManagerData(id)
            ).onSuccess {
                _delegateState.value = Event.Success
            }.onFailure {
                _delegateState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }

    fun accept(id: UUID) {
        viewModelScope.launch {
            applicantAcceptUseCase(
                clubId = _clubId.value,
                body = ClubApplyAcceptData(id)
            ).onSuccess {
                _acceptApplicantState.value = Event.Success
            }.onFailure {
                _acceptApplicantState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }

    fun reject(id: UUID) {
        viewModelScope.launch {
            applicantRejectUseCase(
                clubId = _clubId.value,
                body = ClubApplyRejectData(id)
            ).onSuccess {
                _rejectApplicantState.value = Event.Success
            }.onFailure {
                _rejectApplicantState.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }
}