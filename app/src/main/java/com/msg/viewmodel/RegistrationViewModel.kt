package com.msg.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msg.gcms.base.utils.Event
import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase
) : ViewModel() {
    private val _emailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> get() = _emailCode

    private val _emailPostCheckStatus = MutableLiveData<Event<Boolean>>()
    val emailPostCheckStatus: LiveData<Event<Boolean>> get() = _emailPostCheckStatus

    private val _emailCheckStatus = MutableLiveData<Event<Boolean>>()
    val emailCheckStatus: LiveData<Event<Boolean>> get() = _emailCheckStatus

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> get() = _registrationStatus

    private val _toastString = MutableLiveData<String>()
    val toastString: LiveData<String> get() = _toastString

    private lateinit var email: String

    fun testPostEmailLogic(email: String) {
        when (email) {
            "s21014" -> _emailPostCheckStatus.value = Event(true)
            else -> {
                _toastString.value = "테스트 실패"
                _emailPostCheckStatus.value = Event(false)
            }
        }
    }

    fun postEmailLogic(email: String) =
        viewModelScope.launch {
            this@RegistrationViewModel.email = email
            try {
                val response = useCase.postEmail(CodeIssuanceRequest(email))
                Log.d("TAG", "${response.code()}, ${response.body()}")
                when (response.code()) {
                    201 -> {
                        _emailPostCheckStatus.value = Event(true)
                    }
                    403 -> {
                        _toastString.value = "유효하지 않은 계정입니다."
                        _emailPostCheckStatus.value = Event(false)
                    }
                    409 -> {
                        _toastString.value = "이미 인증된 계정입니다."
                        _emailPostCheckStatus.value = Event(false)
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "error : $e")
            }
            // Log.d("TAG"," ${response.errorBody()}, ${response.isSuccessful}, ${response.headers()}, ${response.message()}")
            // Log.d("postEmail", response.code + response.message)
        }

    fun registrationLogic(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response =
                    useCase.postRegistration(RegisterRequest(email = email, password = password))
                Log.d("TAG", "${response.code()}")
                when (response.code()) {
                    in 200..299 -> true
                    else -> "실패"
                }
            } catch (e: Exception) {
                Log.d("TAG", "error : $e")
            }
        }
    }

    fun testEmailCheckLogic(emailCode: String) {
        when (emailCode) {
            "1111" -> _emailCheckStatus.value = Event(true)
            else -> {
                _emailCheckStatus.value = Event(false)
            }
        }
    }

    fun emailCheckLogic(emailCode: String) {
        viewModelScope.launch {
            try {
                val response = useCase.headCheckCode(email.toString(), emailCode.toString())
                _emailCheckStatus.value = when (response.code()) {
                    200 -> Event(true)
                    else -> Event(false)
                }
                Log.d("TAG", _emailCheckStatus.value.toString())
                Log.d("TAG", "${response.code()}, ${response.body()} $response")
            } catch (e: Exception) {
                Log.d("TAG", "error : $e")
            }
        }
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
