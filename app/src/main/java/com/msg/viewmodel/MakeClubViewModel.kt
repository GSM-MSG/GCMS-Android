package com.msg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msg.gcms.ui.base.BaseViewModel

class MakeClubViewModel : BaseViewModel() {

    private var _clubType = MutableLiveData<String>()
    val clubType : LiveData<String> get() =_clubType

    fun clubTypeChange(type : String) {
        _clubType.value = type
    }

}