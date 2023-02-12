package com.msg.gcms.data.remote.datasource.club_member

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo

interface ClubMemberDataSource {
    suspend fun getMemberList(clubId: Long): MemberInfo

    suspend fun deleteMemberExpel(clubId: Long, body: MemberManagementRequest)

    suspend fun putDelegationOfRepresentation(clubId: Long, body: MemberManagementRequest)
}