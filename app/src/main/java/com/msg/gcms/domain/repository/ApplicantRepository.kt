package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo

interface ApplicantRepository {
    suspend fun getApplicantList(clubId: Long): MemberInfo

    suspend fun postClubApply(clubId: Long)

    suspend fun postApplicantAccept(clubId: Long, body: MemberManagementRequest)

    suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest)

    suspend fun deleteClubApply(clubId: Long)


}