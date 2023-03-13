package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.club_member.delegation_of_manager.DelegationOfManagerRequest
import com.msg.gcms.data.remote.dto.club_member.get_club_member.GetClubMemberResponse
import com.msg.gcms.data.remote.dto.club_member.member_expelled.MemberExpelledRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubMemberAPI {

    @GET("club-member/{club_id}")
    suspend fun getMemberList(
        @Path("club_id") clubId: Long
    ): GetClubMemberResponse

    @POST("club-member/{club_id}")
    suspend fun deleteMemberExpel(
        @Path("club_id") clubId: Long,
        @Body body: MemberExpelledRequest
    ): Response<Unit>

    @PATCH("club-member/{club_id}")
    suspend fun putDelegationOfRepresentation(
        @Path("club_id") clubId: Long,
        @Body body: DelegationOfManagerRequest
    ): Response<Unit>
}