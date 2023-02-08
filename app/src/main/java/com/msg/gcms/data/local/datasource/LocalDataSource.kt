package com.msg.gcms.data.local.datasource

interface LocalDataSource {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun getAccessExp(): String
    suspend fun getRefreshExp(): String
    suspend fun saveToken(access: String, refresh: String, accessExp: String, refreshExp: String)
}