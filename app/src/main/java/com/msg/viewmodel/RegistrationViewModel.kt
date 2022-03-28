package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msg.gcms.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel(), LifecycleObserver {
     private var _emailCode = MutableLiveData<String>().apply { value = "" }
    var emailCode: LiveData<String> = _emailCode

    fun registrationLogic(email: String, password: String) {
        val queries: HashMap<String, String> = HashMap()

        queries["email"] = email
        queries["password"] = password

        viewModelScope.launch {

        }
    }

    fun emailCheckLogic(emailCode: ArrayList<String>) {

    }

    fun typeNumber(num: Int) {
        if (_emailCode.value?.length ?: 0 < 4) {
            val sb = StringBuilder(_emailCode.value)
            sb.append(num.toString())
            _emailCode.postValue(sb.toString())
        }
    }
    fun eraseNumber() {
        if (_emailCode.value?.isNotEmpty() == true) {
            val sb = StringBuilder(_emailCode.value)
            sb.deleteCharAt(sb.length-1)
            _emailCode.postValue(sb.toString())
        }
    }
}