package com.msg.gcms.utils

enum class ErrorType {
    //400
    //club
    CLUB_TYPE_ERROR,
    WRONG_BODY,
    MEMBER_AREADY_APPLICATION_OR_AFFILIATION_IN_OTHER_CLUB,
    //401
    TOKENE_XPIRED,
    //403
    //club
    NOT_CLUB_HEAD,
    CLUB_HEAD_NOT_KICK_SELF,
    //404
    //club
    CLUB_NOTFOUND,
    USER_NOTFOUND,
    //406
    //club
    NOT_CLUB_MEMBER,
    //409
    //club
    CLUB_ALREADY_EXIST,
    USER_AFFILIATION_IN_OTHER_CLUB,
    USER_AREADY_IN_CLUB,

}