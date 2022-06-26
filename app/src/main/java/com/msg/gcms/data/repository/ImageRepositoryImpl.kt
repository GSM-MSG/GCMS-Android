package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.ImageDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.Image.ImageResponse
import com.msg.gcms.domain.repository.ImageRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val datasource: ImageDataSourceImpl
) : ImageRepository {
    override suspend fun postImage(image: MultipartBody.Part): Response<ImageResponse> {
        return datasource.postImage(image = image)
    }
}