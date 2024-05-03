package com.msg.gcms.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.PatchAttendStatusCollectivelyRequestData
import com.msg.gcms.domain.data.attend.PatchAttendStatusRequestData
import com.msg.gcms.domain.data.attend.PostAttendListRequestData
import com.msg.gcms.domain.usecase.attend.GetClubAttendListUseCase
import com.msg.gcms.domain.usecase.attend.PatchAttendStatusCollectivelyUseCase
import com.msg.gcms.domain.usecase.attend.PatchAttendStatusUseCase
import com.msg.gcms.domain.usecase.attend.PostAttendListUseCase
import com.msg.gcms.domain.usecase.auth.SaveTokenInfoUseCase
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AttendViewModel @Inject constructor(
    private val getClubAttendListUseCase: GetClubAttendListUseCase,
    private val patchAttendStatusUseCase: PatchAttendStatusUseCase,
    private val patchAttendStatusCollectivelyUseCase: PatchAttendStatusCollectivelyUseCase,
    private val postClubAttendListUseCase: PostAttendListUseCase,
    private val saveTokenInfoUseCase: SaveTokenInfoUseCase
) : ViewModel() {

    private val _attendList = MutableLiveData<GetClubAttendListResponseData>()
    val attendList: LiveData<GetClubAttendListResponseData> get() = _attendList

    private val _getAttendListState = MutableLiveData<Event>()
    val getAttendListState: LiveData<Event> get() = _getAttendListState

    private val _postAttendListState = MutableLiveData<Event>()
    val postAttendListState: LiveData<Event> get() = _postAttendListState

    private val _patchAttendStatusState = MutableLiveData<Event>()
    val patchAttendStatusState: LiveData<Event> get() = _patchAttendStatusState

    private val _patchAttendStatusCollectivelyState = MutableLiveData<Event>()
    val patchAttendStatusCollectivelyState: LiveData<Event> get() = _patchAttendStatusCollectivelyState

    var postClubAttendData = mutableStateOf(
        PostAttendListRequestData(
            name = "attend",
            date = LocalDate.now(),
            periods = listOf()
        )
    )
        private set

    var clubId = mutableStateOf<Long>(0)
        private set

    var date = mutableStateOf<LocalDate?>(null)
        private set

    var period = mutableStateOf<String?>(null)
        private set

    var selectedStudentList = mutableListOf<Long>()
        private set

    var selectedStudentName = mutableListOf<String>()
        private set

    var attendanceStatus = mutableStateOf("")
        private set

    fun getClubAttendList() = viewModelScope.launch {
        getClubAttendListUseCase(
            clubId = clubId.value,
            date = date.value,
            period = period.value
        ).onSuccess {
            it.catch { remoteError ->
                _getAttendListState.value = remoteError.errorHandling()
            }.collect { response ->
                _attendList.value = response
                _getAttendListState.value = Event.Success
            }
        }.onFailure { error ->
            _getAttendListState.value = error.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
        }
    }

    fun postClubAttendList() = viewModelScope.launch {
        postClubAttendListUseCase(
            body = postClubAttendData.value
        ).onSuccess {
            it.catch { remoteError ->
                _postAttendListState.value = remoteError.errorHandling()
            }.collect {
                _postAttendListState.value = Event.Success
            }
        }.onFailure { error ->
            _postAttendListState.value = error.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
        }
    }

    private fun patchAttendStatus() = viewModelScope.launch {
        patchAttendStatusUseCase(
            body = PatchAttendStatusRequestData(
                attendanceId = selectedStudentList[0],
                attendanceStatus = attendanceStatus.value
            )
        ).onSuccess {
            it.catch { remoteError ->
                _patchAttendStatusState.value = remoteError.errorHandling()
            }.collect {
                _patchAttendStatusState.value = Event.Success
            }
        }.onFailure { error ->
            _patchAttendStatusState.value = error.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
        }
    }

    private fun patchAttendStatusCollectively() = viewModelScope.launch {
        patchAttendStatusCollectivelyUseCase(
            body = PatchAttendStatusCollectivelyRequestData(
                attendanceIds = selectedStudentList,
                attendanceStatus = attendanceStatus.value
            )
        ).onSuccess {
            it.catch { remoteError ->
                _patchAttendStatusCollectivelyState.value = remoteError.errorHandling()
            }.collect {
                _patchAttendStatusCollectivelyState.value = Event.Success
            }
        }.onFailure { error ->
            _patchAttendStatusCollectivelyState.value = error.errorHandling(unauthorizedAction = { saveTokenInfoUseCase() })
        }
    }

    fun useAttendFun() {
        if (selectedStudentList.size > 1) { patchAttendStatusCollectively() } else { patchAttendStatus() }
    }
}