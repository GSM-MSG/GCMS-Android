package com.msg.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditClubViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
): ViewModel() {

    private val _clubInfo = MutableLiveData<ClubInfoResponse?>()
    val clubInfo: LiveData<ClubInfoResponse?> get() = _clubInfo

    private val _clubMember = MutableLiveData<List<UserInfo>>()
    val clubMember : LiveData<List<UserInfo>> get() = _clubMember

    private val _activityPhoto = MutableLiveData<List<Uri>>()
    val activityPhoto: LiveData<List<Uri>> get() = _activityPhoto

    fun getClubInfo(type: String, clubName: String) {
        Log.d("TAG", "getClubInfo: $type, $clubName")
        try {
            viewModelScope.launch {
                val response = getDetailUseCase.getDetail(type = type, clubname = clubName)
                Log.d("TAG", "getClubInfo: ${response.body()}")
                _clubInfo.value = response.body()
                _clubMember.value = response.body()!!.member
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setClubMember(list: List<UserInfo>) {
        _clubMember.value = list
    }

    fun clearClubInfo() {
        _clubInfo.value = null
    }
}