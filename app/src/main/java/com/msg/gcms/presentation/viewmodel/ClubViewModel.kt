package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.applicant.PostClubApplyUseCase
import com.msg.gcms.domain.usecase.applicant.PostClubCancelUseCase
import com.msg.gcms.domain.usecase.club.ClubDeleteUseCase
import com.msg.gcms.domain.usecase.club.PutClubCloseUseCase
import com.msg.gcms.domain.usecase.club.PutClubOpenUseCase
import com.msg.gcms.domain.usecase.user.ExitUseCase
import com.msg.gcms.presentation.base.LottieFragment
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val postClubApplyUseCase: PostClubApplyUseCase,
    private val postClubCancelUseCase: PostClubCancelUseCase,
    private val putClubOpenUseCase: PutClubOpenUseCase,
    private val putClubCloseUseCase: PutClubCloseUseCase,
    private val exitUseCase: ExitUseCase,
    private val clubDeleteUseCase: ClubDeleteUseCase,
) : ViewModel() {

    private val lottie by lazy { LottieFragment() }

    // private val _getClubStatus = MutableLiveData<Event>()
    // val getClubStatus: LiveData<Event> get() = _getClubStatus

    private var _cancelClubApply = MutableLiveData<Event>()
    val cancelClubApply: LiveData<Event> get() = _cancelClubApply

    private var _applyClub = MutableLiveData<Event>()
    val applyClub: LiveData<Event> get() = _applyClub

    private var _closingClubApplication = MutableLiveData<Event>()
    val closingClubApplication: LiveData<Event> get() = _closingClubApplication

    private var _openingClubApplication = MutableLiveData<Event>()
    val openingClubApplication: LiveData<Event> get() = _openingClubApplication

    private val _deleteClub = MutableLiveData<Event>()
    val deleteClub: LiveData<Event> get() = _deleteClub

    private val TAG = "ClubViewModel"

    fun postClubApply(clubId: Long) {
        viewModelScope.launch {
            postClubApplyUseCase(
                clubId = clubId
            ).onSuccess {
                //Todo(Leeyeonbin) 여기도 스테이터스로 예외하는거 다 수정하기
                _applyClub.value = Event.Success
            }.onFailure {
                _applyClub.value = when (it) {
                    is UnauthorizedException, is NeedLoginException -> Event.Unauthorized
                    is ForBiddenException -> Event.ForBidden
                    is NotFoundException -> Event.NotFound
                    is ServerException -> Event.Server
                    else -> {
                        Log.d(TAG, "postClubApply: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun postClubCancel(clubId: Long) {
        viewModelScope.launch {
            postClubCancelUseCase(
                clubId = clubId
            ).onSuccess {
                _cancelClubApply.value = Event.Success
            }.onFailure {
                _cancelClubApply.value = when (it) {
                    is UnauthorizedException, is NeedLoginException -> Event.Unauthorized
                    is NotFoundException -> Event.NotFound
                    is ServerException -> Event.Server
                    else -> {
                        Log.d(TAG, "postClubCancel: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun putClubOpen(clubId: Long) {
        viewModelScope.launch {
            putClubOpenUseCase(
                clubId = clubId
            ).onSuccess {
                _openingClubApplication.value = Event.Success
            }.onFailure {
                _openingClubApplication.value = when (it) {
                    is BadRequestException -> Event.BadRequest
                    is UnauthorizedException, is NeedLoginException -> Event.Unauthorized
                    is ForBiddenException -> Event.ForBidden
                    is NotFoundException -> Event.NotFound
                    is ServerException -> Event.Server
                    else -> {
                        Log.d(TAG, "putClubOpen: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun putClubClose(clubId: Long) {
        viewModelScope.launch {
            putClubCloseUseCase(
                clubId = clubId
            ).onSuccess {
                _closingClubApplication.value = Event.Success
            }.onFailure {
                _closingClubApplication.value = when (it) {
                    is BadRequestException -> Event.BadRequest
                    is UnauthorizedException, is NeedLoginException -> Event.Unauthorized
                    is ForBiddenException -> Event.ForBidden
                    is NotFoundException -> Event.NotFound
                    is ServerException -> Event.Server
                    else -> {
                        Log.d(TAG, "putClubClose: $it")
                        Event.UnKnown
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

    fun exit(clubId: Long) {
        viewModelScope.launch {
            try {
                exitUseCase(clubId = clubId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteClub(clubId: Long) {
        viewModelScope.launch {
            clubDeleteUseCase(
                clubId
            ).onSuccess {
                _deleteClub.value = Event.Success
            }.onFailure {
                _deleteClub.value = when (it) {
                    is BadRequestException -> Event.BadRequest
                    is UnauthorizedException, is NeedLoginException -> Event.Unauthorized
                    is ForBiddenException -> Event.ForBidden
                    is NotFoundException -> Event.NotFound
                    is ServerException -> Event.Server
                    else -> {
                        Log.d(TAG, "deleteClub: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun initializationProperties() {
        _applyClub = MutableLiveData()
        _cancelClubApply = MutableLiveData()
        _openingClubApplication = MutableLiveData()
        _closingClubApplication = MutableLiveData()
    }
}
