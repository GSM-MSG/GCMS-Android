package com.msg.gcms.base.utils

import androidx.lifecycle.Observer


open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            // 이벤트가 이미 처리 되었다면 Null 반환
            null
        } else {
            // 이벤트가 아직 처리되지 않았다면 이벤트를 처리했다고 표시한 후에 content 반환
            hasBeenHandled = true
            content
        }
    }

    // 이벤트 처리 여부와 상관없이 값을 반환
    fun peekContent(): T = content
}

class EventObserver<T> (private val onEventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnHandledContent(value)
        }
    }
}