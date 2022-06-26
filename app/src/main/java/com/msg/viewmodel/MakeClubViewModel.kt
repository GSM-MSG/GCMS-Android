package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.domain.usecase.club.ClubUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MakeClubViewModel @Inject constructor(
    private val useCase: ClubUseCase,
    private val userUserCase: UserUseCase,
    private val imageUseCase: ImageUseCase
) : ViewModel() {

    private var _clubType = MutableLiveData<String>("MAJOR")
    val clubType: LiveData<String> get() = _clubType

    fun clubTypeChange(type: String) {
        _clubType.value = type
    }
    private val _result = MutableLiveData<List<UserData>>()
    val result : LiveData<List<UserData>> get() = _result

    var _memberList = mutableListOf<UserData>()


    fun getSearchUser(name: String) {
        val queryString : HashMap<String,String> = HashMap()
        queryString["name"] = name
        queryString["type"] = clubType.value.toString()
        viewModelScope.launch {
            val response = userUserCase.getSearchUser(queryString)
            when(response.code()){
                200 -> {
                    _result.value = response.body()
                    Log.d("TAG", "searchResult: ${_result.value}")
                }
                else -> {
                    Log.d("TAG", "searchResult: ${response.body()} ")
                }
            }
        }
    }

    fun changeImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            val response = imageUseCase.postImage(image)
            when(response.code()){
                201 -> {
                    Log.d("TAG", "changeImage: ${response.body()}, $response")
                }
                else -> {
                    Log.d("TAG", "changeImage: else ${response.code()}")
                }
            }
        }
    }
}
