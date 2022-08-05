package com.msg.gcms.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SetErrorMsg {

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    fun setErrorMsg(errorType: ErrorType) {
        _errorMsg.value = when (errorType) {
            ErrorType.CLUB_TYPE_ERROR, ErrorType.WRONG_BODY -> "알수 없는 에러가 발생했습니다."
            ErrorType.MEMBER_AREADY_APPLICATION_OR_AFFILIATION_IN_OTHER_CLUB, ErrorType.USER_AFFILIATION_IN_OTHER_CLUB, ErrorType.USER_AREADY_IN_CLUB -> "맴버가 이미 다른동아리에 신청 또는 가입되어있습니다."
            ErrorType.TOKENE_XPIRED -> "토큰이 만료되었습니다, 다시 로그인 해주세요."
            ErrorType.NOT_CLUB_HEAD -> "부장이 아닙니다."
            ErrorType.CLUB_HEAD_NOT_KICK_SELF -> "부장은 자기 자신을 강퇴할수 없습니다."
            ErrorType.CLUB_NOTFOUND -> "동아리를 찾지 못했습니다."
            ErrorType.USER_NOTFOUND -> "유저를 찾지 못했습니다."
            ErrorType.NOT_CLUB_MEMBER -> "동아리 맴버가 아닙니다."
            ErrorType.CLUB_ALREADY_EXIST -> "이미 존재하는 동아리 입니다."
        }
    }
}