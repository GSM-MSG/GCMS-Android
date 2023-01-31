package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.viewmodel.util.Event
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

    private val _showNav = MutableLiveData<Boolean>()
    val showNav: LiveData<Boolean> get() = _showNav

    private val _isProfile = MutableLiveData<Boolean>()
    val isProfile: LiveData<Boolean> get() = _isProfile

    private val _getClubDetail = MutableLiveData<Event>()
    val getClubDetail: LiveData<Event> get() = _getClubDetail

    // private lateinit var clubInfo: ClubInfoResponse

    fun getDetail(type: String, q: String) {
        viewModelScope.launch {
            Log.d(TAG, "타입 : ${type}, 이름 : ${q}")
            getDetailUseCase(
                type, q
            ).onSuccess {
                // Todo(LeeHyeonbin) liveData로 값받아오는거 수정하기
                _result.value = it
                _getClubDetail.value = Event.Success
            }.onFailure {
                _getClubDetail.value = when (it) {
                    is BadRequestException -> {
                        Log.d(TAG, "getDetail: $it.")
                        Event.BadRequest
                    }
                    is UnauthorizedException -> {
                        Log.d(TAG, "getDetail: $it")
                        Event.Unauthorized
                    }
                    is NotFoundException -> {
                        Log.d(TAG, "getDetail: $it")
                        Event.NotFound
                    }
                    else -> {
                        Log.d(TAG, "getDetail: $it")
                        Event.UnKnown
                    }
                }
            }
        }
    }

    // Todo (KimHs) 이름 좀 명시적으로 바꿔주세요
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

    // Todo (KimHs) 이상한걸 만들어놨냐 - 이현빈
    fun clearResult() {
        _result.value = null
    }

    // fun getClubInfo() = clubInfo
}