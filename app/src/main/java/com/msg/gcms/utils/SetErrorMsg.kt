package com.msg.gcms.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SetErrorMsg {

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg : LiveData<String> get() = _errorMsg

    fun setErrorMsg(errorType: ErrorType){
        _errorMsg.value = when(errorType){
            ErrorType.CLUB_TYPE_ERROR -> ""
            ErrorType.WRONG_BODY -> ""
            ErrorType.MEMBER_AREADY_APPLICATION_OR_AFFILIATION_IN_OTHER_CLUB -> ""
            ErrorType.TOKENE_XPIRED -> ""
            ErrorType.NOT_CLUB_HEAD -> ""
            ErrorType.CLUB_HEAD_NOT_KICK_SELF -> ""
            ErrorType.CLUB_NOTFOUND -> ""
            ErrorType.USER_NOTFOUND -> ""
            ErrorType.NOT_CLUB_MEMBER -> ""
            ErrorType.CLUB_ALREADY_EXIST -> ""
            ErrorType.USER_AFFILIATION_IN_OTHER_CLUB -> ""
            ErrorType.USER_AREADY_IN_CLUB -> ""
        }
    }
}