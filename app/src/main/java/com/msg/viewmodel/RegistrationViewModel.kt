package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msg.gcms.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel() {
    private val _emailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> get() = _emailCode

    fun registrationLogic(email: String, password: String) {
        val queries: HashMap<String, String> = HashMap()

        queries["email"] = email
        queries["password"] = password

        viewModelScope.launch {

        }
    }

    fun emailCheckLogic(emailCode: String) {

    }

    fun typeNumber(num: Int) {
        if (_emailCode.value?.length ?: 0 < 4) {
            val sb = StringBuilder(_emailCode.value ?: "")
            sb.append(num.toString())
            _emailCode.postValue(sb.toString())
        }
    }

    fun eraseNumber() {
        if (_emailCode.value?.isNotEmpty() == true) {
            val sb = StringBuilder(_emailCode.value ?: "")
            sb.deleteCharAt(sb.length - 1)
            _emailCode.postValue(sb.toString())
        }
    }

    fun clearNumber() {
        val sb = StringBuilder(_emailCode.value ?: "")
        sb.clear()
        _emailCode.postValue(sb.toString())
    }
}