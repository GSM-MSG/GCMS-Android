package com.msg.gcms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getSearchUserUseCase: GetSearchUserUseCase
) : ViewModel() {
    var clubType = "MAJOR"

    private val _result = MutableLiveData<List<UserData>>()
    val result : LiveData<List<UserData>> get() = _result


    /* 안 쓰는 코드
    fun getSearchUser(name: String) {
        val queryString : HashMap<String,String> = HashMap()
        queryString["name"] = name
        queryString["type"] = clubType
        viewModelScope.launch {
            val response = getSearchUserUseCase(
                userSearch = queryString
            ).onSuccess {
                //Todo(Leeyeonbin) 여기 코드 다 수정하기
                // _result.value = it.body()
                Log.d("TAG", "searchResult: ${_result.value}")
            }.onFailure {
                when(it) {
                    is UnauthorizedException -> Log.d("TAG", "getSearchUser: $it")
                    else -> Log.d("TAG", "getSearchUser: $it")
                }
            }
        }
    }
    */
}