package com.msg.gcms.presentation.viewmodel.util

sealed class HttpStatus {
    object Loading: HttpStatus()
    object Success: HttpStatus()
    object Error: HttpStatus()
}