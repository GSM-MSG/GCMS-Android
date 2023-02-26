package com.msg.gcms.presentation.viewmodel.util

import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.exception.NotAcceptableException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.TimeOutException
import com.msg.gcms.domain.exception.UnauthorizedException

suspend fun Throwable.errorHandling(
    badRequestAction: suspend () -> Unit = {},
    unauthorizedAction: suspend () -> Unit = {},
    forBiddenAction: suspend () -> Unit = {},
    notFoundAction: suspend () -> Unit = {},
    notAcceptableAction: suspend () -> Unit = {},
    timeOutAction: suspend () -> Unit = {},
    conflictAction: suspend () -> Unit = {},
    serverAction: suspend () -> Unit = {},
    unknownAction: suspend () -> Unit = {},
): Event =
    when (this) {
        is BadRequestException -> {
            badRequestAction()
            Event.BadRequest
        }
        is UnauthorizedException, is NeedLoginException -> {
            unauthorizedAction()
            Event.Unauthorized
        }
        is ForBiddenException -> {
            forBiddenAction()
            Event.ForBidden
        }
        is NotFoundException -> {
            notFoundAction()
            Event.NotFound
        }
        is NotAcceptableException -> {
            notAcceptableAction()
            Event.NotAcceptable
        }
        is TimeOutException -> {
            timeOutAction()
            Event.TimeOut
        }
        is ConflictException -> {
            conflictAction()
            Event.Conflict
        }
        is ServerException -> {
            serverAction()
            Event.Server
        }
        else -> {
            unknownAction()
            Event.UnKnown
        }
    }