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
) : ViewModel() {

    private val TAG = "getdetail"

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    private lateinit var _result: ClubInfoResponse
    val result: ClubInfoResponse get() = _result

    fun getDetail(type: String, clubname: String) {
        viewModelScope.launch {
            Log.d(TAG, getDetailStatus.toString())
            try {
                val response = getDetailUseCase.getDetail(type, clubname)
                _result = response.body()!!
                when (response.code()) {
                    200 -> {
                        _getDetailStatus.value = response.code()
                        Log.d(TAG, "status : ${response.code()}")
                    }
                    else -> {
                        _getDetailStatus.value = response.code()
                        Log.d(TAG, "status : ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }
}