package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.ImageDataSourceImpl
import com.msg.gcms.domain.repository.ImageRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val datasource: ImageDataSourceImpl
) : ImageRepository {
    override suspend fun postImage(image: List<MultipartBody.Part>): List<String> {
        return datasource.postImage(image = image)
    }
}