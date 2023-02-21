package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.club_member.delegation_of_manager.DelegationOfManagerData
import com.msg.gcms.domain.data.club_member.get_club_member.GetClubMemberData
import com.msg.gcms.domain.data.club_member.member_expelled.MemberExpelledData

interface ClubMemberRepository {
    suspend fun getMemberList(clubId: Long): GetClubMemberData

    suspend fun deleteMemberExpel(clubId: Long, body: MemberExpelledData)

    suspend fun putDelegationOfRepresentation(clubId: Long, body: DelegationOfManagerData)
}