package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditClubViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
): ViewModel() {

    private val _clubInfo = MutableLiveData<ClubInfoResponse>()
    val clubInfo: LiveData<ClubInfoResponse> get() = _clubInfo

    fun getClubInfo(type: String, clubName: String) {
        try {
            viewModelScope.launch {
                val response = getDetailUseCase.getDetail(type = type, clubname = clubName)
                Log.d("TAG", "getClubInfo: ${response.body()}")
                _clubInfo.value = response.body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}