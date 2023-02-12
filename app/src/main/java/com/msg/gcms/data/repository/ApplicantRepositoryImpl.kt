package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSourceImpl
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class ApplicantRepositoryImpl @Inject constructor(
    private val datasource: ApplicantDataSourceImpl
): ApplicantRepository{
    override suspend fun getApplicantList(clubId: Long): MemberInfo {
        return datasource.getApplicantList(clubId = clubId)
    }

    override suspend fun postClubApply(clubId: Long) {
        return datasource.postClubApply(clubId = clubId)
    }

    override suspend fun postApplicantAccept(clubId: Long, body: MemberManagementRequest) {
        return datasource.postApplicantAccept(clubId = clubId, body = body)
    }

    override suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest) {
        return datasource.postApplicantReject(clubId = clubId, body = body)
    }
}