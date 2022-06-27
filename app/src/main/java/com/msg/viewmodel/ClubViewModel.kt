package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.domain.usecase.club.ClubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val clubUseCase: ClubUseCase
) : ViewModel() {

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
}