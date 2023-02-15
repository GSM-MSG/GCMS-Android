package com.msg.gcms.data.repository

import com.msg.gcms.data.mapper.ApplicantMapper
import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSourceImpl
import com.msg.gcms.domain.data.applicant.get_applicant_list.GetApplicantListData
import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class ApplicantRepositoryImpl @Inject constructor(
    private val dataSource: ApplicantDataSourceImpl
): ApplicantRepository {
    override suspend fun getApplicantList(clubId: Long): GetApplicantListData {
        return ApplicantMapper.mapperToGetApplicantListData(dataSource.getApplicantList(clubId = clubId))
    }

    override suspend fun postClubApply(clubId: Long) {
        return dataSource.postClubApply(clubId = clubId)
    }

    override suspend fun deleteClubApply(clubId: Long) {
        return dataSource.deleteClubApply(clubId = clubId)
    }

    override suspend fun postApplicantAccept(clubId: Long, body: MemberManagementRequest) {
        return dataSource.postApplicantAccept(clubId = clubId, body = body)
    }

    override suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest) {
        return dataSource.postApplicantReject(clubId = clubId, body = body)
    }
}