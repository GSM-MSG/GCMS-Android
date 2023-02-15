package com.msg.gcms.data.remote.datasource.club_member

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.domain.data.club.get_club_member.GetClubMemberResponse

interface ClubMemberDataSource {
    suspend fun getMemberList(clubId: Long): GetClubMemberResponse

    suspend fun deleteMemberExpel(clubId: Long, body: MemberManagementRequest)

    suspend fun putDelegationOfRepresentation(clubId: Long, body: MemberManagementRequest)
}