package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.notification.request.PatchModifyNotificationRequest
import com.msg.gcms.data.remote.dto.notification.request.PostWriteNotificationRequest
import com.msg.gcms.data.remote.dto.notification.response.GetDetailNotificationResponse
import com.msg.gcms.data.remote.dto.notification.response.GetNotificationListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface NotificationAPI {

    @POST("notification/{club_id}")
    fun writeNotification(
        @Body body: PostWriteNotificationRequest
    )

    @GET("notification/{club_id}/all")
    fun getNoticeList(): GetNotificationListResponse

    @GET("notification/{id}")
    fun getDetailNotification(): GetDetailNotificationResponse

    @PATCH("notification/{id}")
    fun patchNotification(
        @Body body: PatchModifyNotificationRequest
    )

    @DELETE("notification/{id}")
    fun deleteNotification()
}