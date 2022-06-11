package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.usecase.GetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClubDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
) : ViewModel() {

    private val TAG = "getdetail"

//    lateinit var result: Response<ClubInfoResponse>

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    fun getDetail(type: String, q: String){
        viewModelScope.launch {
            Log.d(TAG,"타입 : ${type}, 이름 : ${type}")
            try {
                val response = getDetailUseCase.getDetail(type,q)
               // result = response
                when (response.code()) {
                    200 -> {
                        _getDetailStatus.value = response.code()
                        Log.d(TAG, "status : ${response.body()}")
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