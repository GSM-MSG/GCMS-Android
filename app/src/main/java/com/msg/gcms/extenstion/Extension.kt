package com.msg.gcms.extenstion

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


fun EditText.onTextChanged(block: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            block(s, start, before, count)
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
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

