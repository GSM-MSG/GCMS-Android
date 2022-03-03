package com.msg.gcms.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val LayoutResId : Int): Fragment() {
    val binding get() = _binding!!
    private var _binding : B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, LayoutResId, container, false)
        return binding.root
    }

    abstract fun init ()

    protected fun shortToast (msg : String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun longToast (msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}