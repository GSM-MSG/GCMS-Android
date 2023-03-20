package com.msg.gcms.data.local.database

import androidx.room.Database
import com.msg.gcms.data.local.dao.ClubDao
import com.msg.gcms.data.local.entity.ClubEntity

@Database(
    entities = [
        ClubEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GCMSDataBase {
    abstract fun clubDao(): ClubDao
}