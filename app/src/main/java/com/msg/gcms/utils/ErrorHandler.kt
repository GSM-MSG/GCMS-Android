package com.msg.gcms.utils

class ErrorHandler(code: Int, location: ErrorLocation) {
    var errorMsg = when (code) {
        400 -> {

        }
        else -> {}
    }
}