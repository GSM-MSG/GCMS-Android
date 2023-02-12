package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubMemberAPI {

    @GET("club-member/{club_id}")
    suspend fun getMemberList(
        @Path("club_id") clubId: Long
    ): MemberInfo

    @POST("club-member/{club_id}")
    suspend fun deleteMemberExpel(
        @Path("club_id") clubId: Long,
        @Body body: MemberManagementRequest
    )

    @PATCH("club-member/{club_id}")
    suspend fun putDelegationOfRepresentation(
        @Path("club_id") clubId: Long,
        @Body body: MemberManagementRequest
    )
}