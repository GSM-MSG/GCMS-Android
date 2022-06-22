package com.msg.gcms.data.remote.network

import java.net.URLDecoder

data class ApiResult<out T>(
    val status: Status,
    val code: String?,
    val message: String?,
    val data: T?,
    val exception: Exception?
) {
    enum class Status {
        SUCCESS,
        API_ERROR,
        NETWORK_ERROR,
        LOADING,
    }

    companion object {
        fun <T> success(code: String, data: T?): ApiResult<T> {
            return ApiResult(Status.SUCCESS, code, "", data, null)
        }

        fun <T> error(code: String, message: String): ApiResult<T> {
            return ApiResult(
                Status.SUCCESS,
                URLDecoder.decode(code, "UTF-8"),
                URLDecoder.decode(message, "UTF-8"),
                null,
                null
            )
        }

        fun <T> error(exception: Exception?): ApiResult<T> {
            return ApiResult(Status.NETWORK_ERROR, null, null, null, exception)
        }

        fun <T> loading(): ApiResult<T> {
            return ApiResult(Status.LOADING, null, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, code=$code, message=$message, data=$data, error=$exception)"
    }
}
