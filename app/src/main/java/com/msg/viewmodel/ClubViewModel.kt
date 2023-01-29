package com.msg.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.ClubDeleteUseCase
import com.msg.gcms.domain.usecase.club.PostClubApplyUseCase
import com.msg.gcms.domain.usecase.club.PostClubCancelUseCase
import com.msg.gcms.domain.usecase.club.PutClubCloseUseCase
import com.msg.gcms.domain.usecase.club.PutClubOpenUseCase
import com.msg.gcms.domain.usecase.user.ExitUseCase
import com.msg.gcms.ui.base.LottieFragment
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

    private val _getClubStatus = MutableLiveData<Int>()
    val getClubStatus: LiveData<Int> get() = _getClubStatus

    private val TAG = "ClubViewModel"

    fun postClubApply(type: String, q: String) {
        viewModelScope.launch {
            postClubApplyUseCase(
                ClubIdentificationRequest(type = type, q = q)
            ).onSuccess {
                _getClubStatus.value = it.code()
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d(TAG, "postClubApply: $it")
                    is NotFoundException -> Log.d(TAG, "postClubApply: $it")
                    is ConflictException -> Log.d(TAG, "postClubApply: $it")
                    else -> Log.d(TAG, "postClubApply: $it")
                }
            }
        }
    }

    fun postClubCancel(type: String, q: String) {
        viewModelScope.launch {
            postClubCancelUseCase(
                ClubIdentificationRequest(type = type, q = q)
            ).onSuccess {
                _getClubStatus.value = it.code()
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d(TAG, "postClubCancel: $it")
                    is NotFoundException -> Log.d(TAG, "postClubCancel: $it")
                    else -> Log.d(TAG, "postClubCancel: $it")
                }
            }
        }
    }

    fun putClubOpen(type: String, q: String) {
        viewModelScope.launch {
            putClubOpenUseCase(
                ClubIdentificationRequest(type = type, q = q)
            ).onSuccess {
                _getClubStatus.value = it.code()
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d(TAG, "putClubOpen: $it")
                    is ForBiddenException -> Log.d(TAG, "putClubOpen: $it")
                    else -> Log.d(TAG, "putClubOpen: $it")
                }
            }
        }
    }

    fun putClubClose(type: String, q: String) {
        viewModelScope.launch {
            putClubCloseUseCase(
                ClubIdentificationRequest(type = type, q = q)
            ).onSuccess {
                _getClubStatus.value = it.code()
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d(TAG, "putClubClose: $it")
                    is ForBiddenException -> Log.d(TAG, "putClubClose: $it")
                    else -> Log.d(TAG, "putClubClose: $it")
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

    fun exit(q: String, type: String) {
        viewModelScope.launch {
            try {
                exitUseCase(UserDeleteRequest(q = q, type = type))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteClub(q: String, type: String) {
        viewModelScope.launch {
            clubDeleteUseCase(
                ClubIdentificationRequest(q = q, type = type)
            ).onSuccess {
                Log.d(TAG, "deleteClub: ${it.code()}")
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> Log.d(TAG, "deleteClub: $it")
                    is ForBiddenException -> Log.d(TAG, "deleteClub: $it")
                    is NotFoundException -> Log.d(TAG, "deleteClub: $it")
                    else -> Log.d(TAG, "deleteClub: $it")
                }
            }
        }
    }
}