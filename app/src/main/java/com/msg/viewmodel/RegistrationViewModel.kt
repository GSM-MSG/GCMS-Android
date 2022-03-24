package com.msg.viewmodel

import androidx.lifecycle.viewModelScope
import com.msg.gcms.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel constructor(): BaseViewModel() {
    fun registrationLogic(email : String, password : String) {
        val queries : HashMap<String, String> = HashMap()

        queries["email"] = email
        queries["password"] = password

        viewModelScope.launch {

        }
    }
}