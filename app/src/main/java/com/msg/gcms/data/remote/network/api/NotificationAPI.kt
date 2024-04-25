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
import retrofit2.http.Path

interface NotificationAPI {

    @POST("notification/{club_id}")
    fun writeNotification(
        @Path("club_id") clubId: Long,
        @Body body: PostWriteNotificationRequest
    )

    @GET("notification/{club_id}/all")
    fun getNoticeList(
        @Path("club_id") clubId: Long
    ): GetNotificationListResponse

    @GET("notification/{id}")
    fun getDetailNotification(
        @Path("id") id: Long
    ): GetDetailNotificationResponse

    @PATCH("notification/{id}")
    fun patchNotification(
        @Path("id") id: Long
    ): PatchModifyNotificationRequest

    @DELETE("notification/{id}")
    fun deleteNotification(
        @Path("id") id: Long
    )
}