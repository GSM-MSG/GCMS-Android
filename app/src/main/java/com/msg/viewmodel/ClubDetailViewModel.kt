package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.usecase.GetDetailUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
) : ViewModel() {

    private val TAG = "getdetail"

    lateinit var result: ClubInfoResponse

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    fun getDetail(type: String, clubname: String){
        viewModelScope.launch {
            try {
                val response = getDetailUseCase.getDetail(type, clubname)
                result = response.body()!!
                when (response.code()) {
                    200 -> {
                        _getDetailStatus.value = response.code()
                        Log.d(TAG, "status : ${response.code()}")
                    }
                    else -> {
                        _getDetailStatus.value = response.code()
                        Log.d(TAG,"status : ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "error : $e")
            }
        }
    }
}