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
class ClubDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
): ViewModel() {

    private val TAG = "GetDetailViewModel"

    private val _result = MutableLiveData<ClubInfoResponse?>()
    val result: LiveData<ClubInfoResponse?> get() = _result

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    private val _showNav = MutableLiveData<Boolean>()
    val showNav: LiveData<Boolean> get() = _showNav

    fun getDetail(type: String, q: String) {
        viewModelScope.launch {
            Log.d(TAG, "타입 : ${type}, 이름 : ${q}")
            try {
                val response = getDetailUseCase.getDetail(type, q)
                _getDetailStatus.value = response.code()
                _result.value = response.body()
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, "status : ${response.code()}")
                        Log.d(TAG, "body : ${response.body()}")
                    }
                    else -> {
                        Log.d(TAG, "status : ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }

    fun setNav(boolean: Boolean) {
        _showNav.value = boolean
    }

    fun clearResult() {
        _result.value = null
    }
}