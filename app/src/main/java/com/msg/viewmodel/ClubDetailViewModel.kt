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

    lateinit var result: Response<ClubInfoResponse>

    private val _getDetailStatus = MutableLiveData<Int>()
    val getDetailStatus: LiveData<Int> get() = _getDetailStatus

    private val _type = MutableLiveData<String>()
    private val _q = MutableLiveData<String>()

    fun getDetail(){
        viewModelScope.launch {
            try {
                val response = getDetailUseCase.getDetail(_type.value!!, _q.value!!)
                result = response
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

    fun setDetailInfo(type: String, q: String) {
        _type.value = type
        _q.value = q
    }
}