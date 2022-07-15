package com.msg.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.domain.usecase.club.ClubDeleteUseCase
import com.msg.gcms.domain.usecase.club.ClubUseCase
import com.msg.gcms.domain.usecase.user.ExitUseCase
import com.msg.gcms.ui.base.LottieFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val clubUseCase: ClubUseCase,
    private val exitUseCase: ExitUseCase,
    private val clubDeleteUseCase: ClubDeleteUseCase
) : ViewModel() {

    private val lottie by lazy { LottieFragment() }

    private val _getClubStatus = MutableLiveData<Int>()
    val getClubStatus: LiveData<Int> get() = _getClubStatus

    private val TAG = "ClubViewModel"

    fun postClubApply(type: String, q: String) {
        viewModelScope.launch {
            try {
                val response =
                    clubUseCase.postClubApply(ClubIdentificationRequest(type = type, q = q))
                _getClubStatus.value = response.code()
                checkStatus(response.code())
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    fun postClubCancel(type: String, q: String) {
        viewModelScope.launch {
            try {
                val response =
                    clubUseCase.postClubCancel(ClubIdentificationRequest(type = type, q = q))
                _getClubStatus.value = response.code()
                checkStatus(response.code())
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    fun putClubOpen(type: String, q: String) {
        viewModelScope.launch {
            try {
                val response =
                    clubUseCase.putClubOpen(ClubIdentificationRequest(type = type, q = q))
                _getClubStatus.value = response.code()
                checkStatus(response.code())
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    fun putClubClose(type: String, q: String) {
        viewModelScope.launch {
            try {
                val response =
                    clubUseCase.putClubClose(ClubIdentificationRequest(type = type, q = q))
                _getClubStatus.value = response.code()
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    private fun checkStatus(code: Int) {
        when (code) {
            201 -> {
                Log.d(TAG, "status: $code")
            }
            else -> {
                Log.d(TAG, "status: $code")
            }
        }
    }

    fun startLottie(fragmentManager: FragmentManager) {
        if(!lottie.isAdded){
            lottie.show(fragmentManager,"Lottie")
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
                exitUseCase.postExit(UserDeleteRequest(q = q, type = type))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteClub(q: String, type: String) {
        viewModelScope.launch {
            try {
                val response = clubDeleteUseCase.postDelete(ClubIdentificationRequest(q = q, type = type))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}