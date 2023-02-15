package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club_member.get_club_member.GetClubMemberResponse

interface ClubMemberRepository {
    suspend fun getMemberList(clubId: Long): GetClubMemberResponse

    suspend fun deleteMemberExpel(clubId: Long, body: MemberManagementRequest)

    suspend fun putDelegationOfRepresentation(clubId: Long, body: MemberManagementRequest)
}