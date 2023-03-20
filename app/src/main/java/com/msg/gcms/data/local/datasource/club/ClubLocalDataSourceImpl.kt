package com.msg.gcms.data.local.datasource.club

import com.msg.gcms.data.local.dao.ClubDao
import com.msg.gcms.data.local.entity.ClubEntity
import javax.inject.Inject

class ClubLocalDataSourceImpl @Inject constructor(
    private val clubDao: ClubDao
): ClubLocalDataSource {
    override fun getClubData(type: String): List<ClubEntity> {
        return clubDao.getClubList(type = type)
    }

    override fun insertClubData(clubData: List<ClubEntity>) {
        return clubDao.insertClubData(clubEntity = clubData)
    }

    override fun deleteClubData(type: String) {
        return clubDao.deleteClubData(type = type)
    }
}