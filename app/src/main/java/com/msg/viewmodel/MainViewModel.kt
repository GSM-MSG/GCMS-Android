package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.UnauthorizedException
import com.msg.gcms.domain.usecase.club.GetClubListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getClubListUseCase: GetClubListUseCase
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
                getClubListUseCase(
                    when (clubName.value) {
                        "전공동아리" -> "MAJOR"
                        "자율동아리" -> "FREEDOM"
                        "사설동아리" -> "EDITORIAL"
                        else -> "MAJOR"
                    }
                ).onSuccess {
                    _clubData.value = it.body()
                }.onFailure {
                    when (it) {
                        is BadRequestException -> Log.d("TAG", "getClubList: $it")
                        is UnauthorizedException -> Log.d("TAG", "getClubList: $it")
                        else -> Log.d("TAG", "getClubList: $it")
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "getClubList: ${e.message}")
            }
        }
    }
}
