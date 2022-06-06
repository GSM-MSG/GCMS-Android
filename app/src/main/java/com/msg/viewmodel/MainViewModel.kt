package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.domain.usecase.club.ClubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val clubUseCase: ClubUseCase
) : ViewModel() {
    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String>
        get() = _clubName

    private val _clubData = MutableLiveData<List<SummaryClubResponse>>()
    val clubData: LiveData<List<SummaryClubResponse>> get() = _clubData

    fun setClubName(position: Int) {
        when (position) {
            0 -> _clubName.value = "전공동아리"
            1 -> _clubName.value = "자율동아리"
            2 -> _clubName.value = "사설동아리"
        }
    }

    fun getClubList() {
        viewModelScope.launch {
            try {
                val response = clubUseCase.getClubList(
                    when (clubName.value) {
                        "전공동아리" -> "MAJOR"
                        "자율동아리" -> "EDITORIAL"
                        "사설동아리" -> "FREEDOM"
                        else -> "MAJOR"
                    }
                )
                _clubData.value = response.body()
            } catch (e: Exception){
                Log.d("ERROR", "getClubList: ${e.message}")
            }
        }
    }
}
