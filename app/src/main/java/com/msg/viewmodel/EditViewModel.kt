package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
): ViewModel() {

    private val _clubInfo = MutableLiveData<ClubInfoResponse>()
    val clubInfo: LiveData<ClubInfoResponse> get() = _clubInfo

    private val _clubType = MutableLiveData<String>()
    val clubType: LiveData<String> get() = _clubType

    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String> get() = _clubName

    fun clubTypeDivider(clubType:String) {
        val clubType = clubType.split("+")
        this._clubType.value = clubType[1].trim()
        this._clubName.value = clubType[0].trim()
        Log.d("TAG", "clubTypeDivider: ${this.clubType.value}, ${this.clubName.value}")
    }

    fun getClubInfo() {
        viewModelScope.launch {
            try {
                Log.d("TAG", "getClubInfo: ${_clubType.value.toString()}, ${_clubName.value.toString()}")
                val response = getDetailUseCase.getDetail(type = _clubType.value.toString(), clubName = _clubName.value.toString())
                _clubInfo.value = response.body()
                when(response.code()) {
                    200 -> {
                        Log.d("TAG", "getClubInfo: ${response.body()}")
                    }
                    else -> {
                        Log.d("TAG", "getClubInfo: ${response.code()}, ${response.body()}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}