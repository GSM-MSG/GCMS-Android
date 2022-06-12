package com.msg.gcms.ui.base

import com.msg.gcms.data.remote.network.ApiResult
import retrofit2.Response

open class BaseRepository {

    internal suspend fun <T> getResponse(response: Response<T>): ApiResult<T> {
        return try{
           if(response.isSuccessful) {
               return ApiResult.success("000000", response.body())
           } else {
               val code = response.code()
               val message = response.message()
               ApiResult.error(code.toString(), message)
           }
         }catch (e: Exception) {
             ApiResult.error(e)
         }
    }
}