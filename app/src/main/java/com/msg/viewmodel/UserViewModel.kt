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

    private val _result = MutableLiveData<UserData>()
    val result : LiveData<UserData> get() = _result

    fun getSearchUser(queryString: String) {
        viewModelScope.launch {
            val queryString : HashMap<String,String> = HashMap()
            queryString["name"] = queryString.toString()
            queryString["type"] = clubType
            val response = userCase.getSearchUser(queryString)
            Log.d("TAG", "getSearchUser: $response")
        }
    }
}