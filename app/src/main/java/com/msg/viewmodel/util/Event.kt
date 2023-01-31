package com.msg.viewmodel.util

sealed class Event (
) {
    object Success: Event()
    object BadRequest: Event()
    object ForBidden: Event()
    object NotFound: Event()
    object UnKnown: Event()
    object TooMuch: Event()
    object Unauthorized: Event()
}
// object Success: Event()