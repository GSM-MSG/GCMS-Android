package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _clubType = MutableStateFlow("")

    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        searchUser()
    }

    fun setClubType(type: String) {
        _clubType.value = type
    }
    private fun searchUser() {
        viewModelScope.launch {
            val response = repository.getUserSearch(UserSearchRequest(_searchQuery.value, _clubType.value.toString()))
            if(response.isSuccessful) {
                Log.d("TAG", "searchUser: ${response.body()}")
            }
        }
    }
}