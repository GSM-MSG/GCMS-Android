package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSourceImpl
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.domain.repository.ClubMemberRepository
import javax.inject.Inject

class ClubMemberRepositoryImpl @Inject constructor(
    private val datasource: ClubMemberDataSourceImpl
) : ClubMemberRepository {
    override suspend fun getMemberList(clubId: Long): MemberInfo {
        return datasource.getMemberList(clubId = clubId)
    }

    override suspend fun deleteMemberExpel(clubId: Long, body: MemberManagementRequest) {
        return datasource.deleteMemberExpel(clubId = clubId, body = body)
    }

    override suspend fun putDelegationOfRepresentation(
        clubId: Long,
        body: MemberManagementRequest
    ) {
        return datasource.putDelegationOfRepresentation(clubId = clubId, body = body)
    }
}