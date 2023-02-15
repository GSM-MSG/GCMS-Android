package com.msg.gcms.data.remote.datasource.club_member

import com.msg.gcms.data.remote.dto.club_member.delegation_of_manager.DelegationOfManagerRequest
import com.msg.gcms.data.remote.dto.club_member.get_club_member.GetClubMemberResponse
import com.msg.gcms.data.remote.dto.club_member.member_expelled.MemberExpelledRequest

interface ClubMemberDataSource {
    suspend fun getMemberList(clubId: Long): GetClubMemberResponse

    suspend fun deleteMemberExpel(clubId: Long, body: MemberExpelledRequest)

    suspend fun putDelegationOfRepresentation(clubId: Long, body: DelegationOfManagerRequest)
}