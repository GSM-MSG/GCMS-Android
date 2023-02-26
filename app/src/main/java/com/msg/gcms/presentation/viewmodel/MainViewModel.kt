package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.domain.usecase.club.GetClubListUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getClubListUseCase: GetClubListUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {
    private val _clubName = MutableLiveData<String>()
    val clubName: LiveData<String>
        get() = _clubName

    private val _clubData = MutableLiveData<List<GetClubListData>>()
    val clubData: LiveData<List<GetClubListData>> get() = _clubData

    private var _getClubList = MutableLiveData<Event>()
    val getClubList: LiveData<Event> get() = _getClubList

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
                _getClubList.value = Event.Success
                _clubData.value = it
            }.onFailure {
                _getClubList.value =
                    it.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
            }
        }
    }
}
