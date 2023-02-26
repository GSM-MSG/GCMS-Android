package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {

    private val TAG = "GetDetailViewModel"

    private var _result = MutableLiveData<ClubDetailData>()
    val result: LiveData<ClubDetailData> get() = _result

    private val _showNav = MutableLiveData<Boolean>()
    val showNav: LiveData<Boolean> get() = _showNav

    private val _isProfile = MutableLiveData<Boolean>()
    val isProfile: LiveData<Boolean> get() = _isProfile

    private var _getClubDetail = MutableLiveData<Event>()
    val getClubDetail: LiveData<Event> get() = _getClubDetail

    private val _refreshClubDetail = MutableLiveData<Event>()
    val refreshClubDetail: LiveData<Event> get() = _refreshClubDetail

    fun getDetail(clubId: Long) {
        viewModelScope.launch {
            getDetailUseCase(
                clubId = clubId
            ).onSuccess {
                _result.value = it
                _getClubDetail.value = Event.Success
            }.onFailure {
                _getClubDetail.value = when (it) {
                    is BadRequestException -> {
                        Log.d(TAG, "getDetail: $it.")
                        Event.BadRequest
                    }
                    is UnauthorizedException, is NeedLoginException -> {
                        Log.d(TAG, "getDetail: $it")
                        saveTokenInfoUseCase()
                        Event.Unauthorized
                    }
                    is NotFoundException -> {
                        Log.d(TAG, "getDetail: $it")
                        Event.NotFound
                    }
                    else -> {
                        Log.d(TAG, "getDetail: ${it.message}")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun refreshDetailInfo(clubId: Long) {
        viewModelScope.launch {
            getDetailUseCase(
                clubId = clubId
            ).onSuccess {
                _result.value = it
                _refreshClubDetail.value = Event.Success
            }.onFailure {
                _refreshClubDetail.value = when (it) {
                    is BadRequestException -> {
                        Log.d(TAG, "getDetail: $it.")
                        Event.BadRequest
                    }
                    is UnauthorizedException, is NeedLoginException -> {
                        Log.d(TAG, "getDetail: $it")
                        saveTokenInfoUseCase()
                        Event.Unauthorized
                    }
                    is NotFoundException -> {
                        Log.d(TAG, "getDetail: $it")
                        Event.NotFound
                    }
                    else -> {
                        Log.d(TAG, "getDetail: ${it.message}")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    fun setNav(boolean: Boolean) {
        _showNav.value = boolean
    }

    fun setIsProfile(boolean: Boolean) {
        _isProfile.value = boolean
    }

    fun initializationProperties() {
        _result = MutableLiveData()
        _getClubDetail = MutableLiveData()
    }
}