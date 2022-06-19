package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userCase: UserUseCase
) : ViewModel() {
    var clubType = "MAJOR"

    private val _result = MutableLiveData<List<UserData>>()
    val result : LiveData<List<UserData>> get() = _result


    fun getSearchUser(name: String) {
        val queryString : HashMap<String,String> = HashMap()
        queryString["name"] = name
        queryString["type"] = clubType
        viewModelScope.launch {
            val response = userCase.getSearchUser(queryString)
            when(response.code()){
               200 -> {
                   _result.value = response.body()
                   Log.d("TAG", "searchResult : ${_result.value}")
               }
              else -> {

              }
            }
        }
    }
}