package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.usecase.user.UserUserCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userCase: UserUserCase
) : ViewModel() {
    private val _clubType = MutableLiveData<String>()

    private val _searchQuery = MutableLiveData<String>()

    private val _userDataList = MutableLiveData<List<UserData>>()
    val userDataList : LiveData<List<UserData>> get() = _userDataList

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        searchUser()
    }

    fun setClubType(type: String) {
        _clubType.value = type
    }
    private fun searchUser() {
        viewModelScope.launch {
            try{
                val response = userCase.getSearchUser(UserSearchRequest(_searchQuery.value.toString(), _clubType.value.toString()))
                when(response.code()){
                    200 -> {
                        _userDataList.value = response.body()
                        Log.d("TAG", "searchUser: ${userDataList.value}")
                    }
                }

            }catch(e: Exception){

            }
        }
    }
}