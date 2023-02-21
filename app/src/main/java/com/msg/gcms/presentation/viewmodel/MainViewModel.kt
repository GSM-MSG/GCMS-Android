package com.msg.gcms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
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

    private val _clubData = MutableLiveData<List<GetClubListData>>()
    val clubData: LiveData<List<GetClubListData>> get() = _clubData

    companion object {
        const val tag = "TAG"
    }

    fun setClubName(position: Int) {
        when (position) {
            0 -> _clubName.value = "전공동아리"
            1 -> _clubName.value = "자율동아리"
            2 -> _clubName.value = "사설동아리"
        }
    }

    fun getClubList() {
        viewModelScope.launch {
            getClubListUseCase(
                when (clubName.value) {
                    "전공동아리" -> "MAJOR"
                    "자율동아리" -> "FREEDOM"
                    "사설동아리" -> "EDITORIAL"
                    else -> "MAJOR"
                }
            ).onSuccess {
                _clubData.value = it
                Log.d(tag, "getClubList: $it")
            }.onFailure {
                when (it) {
                    is BadRequestException -> Log.d(tag, "getClubList: $it")
                    is UnauthorizedException -> Log.d(tag, "getClubList: $it")
                    else -> Log.d(tag, "getClubList: $it")
                }
            }
        }
    }
}
