package com.msg.viewmodel

import androidx.lifecycle.viewModelScope
import com.msg.gcms.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel constructor() : BaseViewModel() {

    private var userEmail: String = ""

    fun registrationLogic(email: String, password: String) {
        userEmail = email

        val queries: HashMap<String, String> = HashMap()
        queries["email"] = email
        queries["password"] = password

        viewModelScope.launch {

        }
    }

    fun emailCheckLogic(code: String) {
        val queries: HashMap<String, String> = HashMap()
        queries["email"] = userEmail
        queries["code"] = code

        viewModelScope.launch {

        }
    }
}