package com.msg.gcms.domain.usecase.img

import com.msg.gcms.domain.repository.ImageRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend fun postImg(img: List<MultipartBody.Part>) = repository.postImage(img)
}