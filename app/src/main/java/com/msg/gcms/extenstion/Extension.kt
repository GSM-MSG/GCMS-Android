package com.msg.gcms.extenstion

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding4.InitialValueObservable
import io.reactivex.rxjava3.android.MainThreadDisposable
import io.reactivex.rxjava3.core.Observer

fun TextView.textChanges(): InitialValueObservable<CharSequence> {
    return TextviewTextChangesObservable(this)
}

private class TextviewTextChangesObservable(
    private val view: TextView
) : InitialValueObservable<CharSequence>() {
    override fun subscribeListener(observer: Observer<in CharSequence>) {
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.addTextChangedListener(listener)
    }
    override val initialValue get() = view.text

    private class Listener(
        private val view: TextView,
        private val observer: Observer<in CharSequence>
    ) : MainThreadDisposable(), TextWatcher {
        override fun onDispose() {
            view.removeTextChangedListener(this)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!isDisposed) {
                observer.onNext(p0)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }
}

