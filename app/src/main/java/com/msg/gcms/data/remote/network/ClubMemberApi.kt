package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ClubMemberApi {

    @GET("club/members")
    suspend fun getMemberList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): MemberInfo

    @POST("club/kick")
    suspend fun deleteMemberExpel(
        @Body body: MemberManagementRequest
    )

    @PUT("club/delegation")
    suspend fun putDelegationOfRepresentation(
        @Body body: MemberManagementRequest
    )
}