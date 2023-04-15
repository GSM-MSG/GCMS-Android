package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSource
import com.msg.gcms.data.remote.dto.club_member.delegation_of_manager.DelegationOfManagerRequest
import com.msg.gcms.data.remote.dto.club_member.get_club_member.toClubMemberData
import com.msg.gcms.data.remote.dto.club_member.member_expelled.MemberExpelledRequest
import com.msg.gcms.domain.data.club_member.delegation_of_manager.DelegationOfManagerData
import com.msg.gcms.domain.data.club_member.get_club_member.GetClubMemberData
import com.msg.gcms.domain.data.club_member.member_expelled.MemberExpelledData
import com.msg.gcms.domain.repository.ClubMemberRepository
import javax.inject.Inject

class ClubMemberRepositoryImpl @Inject constructor(
    private val datasource: ClubMemberDataSource
) : ClubMemberRepository {
    override suspend fun getMemberList(clubId: Long): GetClubMemberData {
        return datasource.getMemberList(clubId = clubId).toClubMemberData()
    }

    override suspend fun deleteMemberExpel(
        clubId: Long,
        body: MemberExpelledData
    ) {
        return datasource.deleteMemberExpel(
            clubId = clubId,
            body = MemberExpelledRequest(uuid = body.uuid)
        )
    }

    override suspend fun putDelegationOfRepresentation(
        clubId: Long,
        body: DelegationOfManagerData
    ) {
        return datasource.putDelegationOfRepresentation(
            clubId = clubId,
            body = DelegationOfManagerRequest(uuid = body.uuid)
        )
    }
}