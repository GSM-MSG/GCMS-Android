package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userCase: UserUseCase
) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    var clubType = "major"

    val result = searchQuery
        .filter {
            it.isNotEmpty()
        }
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest {
            flow {
                Log.d("TAG", "query : $it")
                userCase.getSearchUser(UserSearchRequest(name = it, type = clubType))
                emit(it)
            }
        }
        .flowOn(Dispatchers.Default)
        .catch { e: Throwable -> e.stackTraceToString() }
}