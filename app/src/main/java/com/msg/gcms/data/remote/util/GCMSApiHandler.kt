package com.msg.gcms.data.remote.util

import com.msg.gcms.domain.exception.BadRequestException
import com.msg.gcms.domain.exception.ConflictException
import com.msg.gcms.domain.exception.ForBiddenException
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.exception.NoInternetException
import com.msg.gcms.domain.exception.NotFoundException
import com.msg.gcms.domain.exception.OtherHttpException
import com.msg.gcms.domain.exception.ServerException
import com.msg.gcms.domain.exception.TimeOutException
import com.msg.gcms.domain.exception.UnKnownException
import com.msg.gcms.domain.exception.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GCMSApiHandler<T> {
    private lateinit var httpRequest: suspend () -> T

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    suspend fun sendRequest(): T {
        return try {
            withContext(Dispatchers.IO) {
                httpRequest.invoke()
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
        } catch (e: SocketTimeoutException) {
            throw TimeOutException(message = e.message)
        } catch (e: UnknownHostException) {
            throw NoInternetException()
        } catch (e: NeedLoginException) {
            throw NeedLoginException()
        } catch (e: Exception) {
            throw UnKnownException(message = e.message)
        }
    }
}