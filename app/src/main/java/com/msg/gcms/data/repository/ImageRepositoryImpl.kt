package com.msg.gcms.data.repository

import com.msg.gcms.data.mapper.ImageMapper
import com.msg.gcms.data.remote.datasource.image.ImageDataSource
import com.msg.gcms.domain.data.image.ImageData
import com.msg.gcms.domain.repository.ImageRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val datasource: ImageDataSource
) : ImageRepository {
    override suspend fun postImage(image: List<MultipartBody.Part>): ImageData {
        return ImageMapper.mapperToImageData(datasource.postImage(image = image))
    }
}