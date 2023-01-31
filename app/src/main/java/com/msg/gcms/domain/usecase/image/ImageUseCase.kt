package com.msg.gcms.domain.usecase.image

import com.msg.gcms.domain.repository.ImageRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(image: List<MultipartBody.Part>) = kotlin.runCatching {
        imageRepository.postImage(image = image)
    }
}