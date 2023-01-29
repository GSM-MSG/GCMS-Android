package com.msg.gcms.data.remote.util

import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.OtherHttpException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun <T> gcmsApiHandler(
    crossinline function: suspend () -> T
): T {
    return try {
        withContext(Dispatchers.IO) {
            function.invoke()
        }
    } catch (e: HttpException) {
        val message = e.message
        throw when (e.code()) {
            400 -> BadRequestException(
                message = message
            )
            401 -> UnauthorizedException(
                message = message,
            )
            403 -> ForBiddenException(
                message = message,
            )
            404 -> NotFoundException(
                message = message,
            )
            409 -> ConflictException(
                message = message,
            )
            500, 501, 502, 503 -> ServerException(
                message = message,
            )
            else -> OtherHttpException(
                message = message,
                code = e.code()
            )
        }
    }
}