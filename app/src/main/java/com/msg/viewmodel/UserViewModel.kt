package com.msg.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserViewModel @Inject constructor(
    repository: UserRepository
) : ViewModel() {
    private val SEARCH_TIMEOUT = 300L

    private val _clubType = MutableLiveData<String>()

    fun setClubType(type: String) {
        _clubType.value = type
    }

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchResult = queryChannel
        .asFlow()
        .debounce(SEARCH_TIMEOUT)
        .mapLatest { text ->
            withContext(Dispatchers.IO) {
                repository.getUserSearch(UserSearchRequest(text,_clubType.value.toString()))
            }
        }
        .flowOn(Dispatchers.Default)
        .catch { e: Throwable ->
            e.printStackTrace()
        }
        .asLiveData()
}