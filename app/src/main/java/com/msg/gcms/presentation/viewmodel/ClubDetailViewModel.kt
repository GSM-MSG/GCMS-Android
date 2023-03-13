package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
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

    private val _isProfile = MutableLiveData<Boolean>(false)
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
                _getClubDetail.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
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
                _refreshClubDetail.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
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