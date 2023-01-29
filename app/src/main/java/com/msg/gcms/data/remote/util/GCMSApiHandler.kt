package com.msg.gcms.data.remote.util

import com.msg.gcms.domain.exception.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GCMSApiHandler<T> {
    suspend inline fun invoke(
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
        } catch (e: SocketTimeoutException) {
            throw TimeOutException(message = e.message)
        } catch (e: UnknownHostException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnKnownException(message = e.message)
        }
    }
}