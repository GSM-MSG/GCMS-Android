package com.msg.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class UserViewModel @Inject constructor(
    repository: UserRepository
) : ViewModel() {
    private val _clubType = MutableLiveData<String>()

    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setClubType(type: String) {
        _clubType.value = type
    }

}