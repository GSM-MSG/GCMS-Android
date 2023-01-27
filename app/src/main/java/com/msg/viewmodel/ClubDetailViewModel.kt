package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
) : ViewModel() {

    private val TAG = "GetDetailViewModel"

    private val _result = MutableLiveData<ClubInfoResponse?>()
    val result: LiveData<ClubInfoResponse?> get() = _result

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    private val _showNav = MutableLiveData<Boolean>()
    val showNav: LiveData<Boolean> get() = _showNav

    private val _isProfile = MutableLiveData<Boolean>()
    val isProfile: LiveData<Boolean> get() = _isProfile

    fun getDetail(type: String, q: String) {
        viewModelScope.launch {
            Log.d(TAG, "타입 : ${type}, 이름 : ${q}")
            try {
                getDetailUseCase(type, q).onSuccess {
                    _getDetailStatus.value = it.code()
                    _result.value = it.body()
                }.onFailure {
                    when (it) {
                        is BadRequestException -> Log.d(TAG, "getDetail: $it")
                        is UnauthorizedException -> Log.d(TAG, "getDetail: $it")
                        is NotFoundException -> Log.d(TAG, "getDetail: $it")
                        else -> Log.d(TAG, "getDetail: $it")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    fun setResult(myClubResult: ClubInfoResponse) {
        if (_result.value == null) {
            _result.value = myClubResult
        }
    }

    fun setNav(boolean: Boolean) {
        _showNav.value = boolean
    }

    fun setIsProfile(boolean: Boolean) {
        _isProfile.value = boolean
    }

    fun clearResult() {
        _result.value = null
    }
}