package com.msg.gcms.util

fun String.removeDot(): String {
    return this.replace("^\"|\"$".toRegex(), "")
}