 package com.msg.gcms.extenstion

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

// fun EditText.onTextChanged(block: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
//     addTextChangedListener(object : TextWatcher {
//         override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//         }
//
//         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//             block(s, start, before, count)
//         }
//
//         override fun afterTextChanged(p0: Editable?) {
//         }
//     })
// }

fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
                trySend(p0)
            }
        }
        addTextChangedListener(listener)
        // 콜백이 사라질 때 실행
        awaitClose{
            removeTextChangedListener(listener)
        }
    }.onStart {
        Log.d("TAG", "textChangesToFlow: ")
        emit(text)
    }
}

// fun TextView.textChanges(): InitialValueObservable<CharSequence> {
//     return TextviewTextChangesObservable(this)
// }
//
// private class TextviewTextChangesObservable(
//     private val view: TextView
// ) : InitialValueObservable<CharSequence>() {
//     override fun subscribeListener(observer: Observer<in CharSequence>) {
//         val listener = Listener(view, observer)
//         observer.onSubscribe(listener)
//         view.addTextChangedListener(listener)
//     }
//     override val initialValue get() = view.text
//
//     private class Listener(
//         private val view: TextView,
//         private val observer: Observer<in CharSequence>
//     ) : MainThreadDisposable(), TextWatcher {
//         override fun onDispose() {
//             view.removeTextChangedListener(this)
//         }
//
//         override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//         }
//
//         override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//             if(!isDisposed) {
//                 observer.onNext(p0)
//             }
//         }
//
//         override fun afterTextChanged(p0: Editable?) {
//         }
//     }
// }

