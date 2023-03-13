package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.user.get_my_profile.GetMyProfileResponse
import com.msg.gcms.data.remote.dto.user.get_my_profile.ProfileClubResponse
import com.msg.gcms.data.remote.dto.user.get_profile_image.GetProfileImageResponse
import com.msg.gcms.data.remote.dto.user.search_user.GetSearchUserResponse
import com.msg.gcms.domain.data.user.get_my_profile.GetMyProfileData
import com.msg.gcms.domain.data.user.get_my_profile.ProfileClubData
import com.msg.gcms.domain.data.user.get_profile_image.GetProfileImageData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData

object UserMapper {

    fun mapperToGetMyProfileData(data: GetMyProfileResponse): GetMyProfileData {
        return GetMyProfileData(
            classNum = data.classNum,
            clubs = data.clubs.map { mapperToProfileClubData(it) },
            email = data.email,
            grade = data.grade,
            name = data.name,
            number = data.number,
            profileImg = data.profileImg,
            uuid = data.uuid
        )
    }

    private fun mapperToProfileClubData(data: ProfileClubResponse): ProfileClubData {
        return ProfileClubData(
            id = data.id,
            type = data.type,
            bannerImg = data.bannerImg,
            title = data.title
        )
    }

    fun mapperToGetSearchUserData(data: GetSearchUserResponse): GetSearchUserData {
        return GetSearchUserData(
            classNum = data.classNum,
            email = data.email,
            grade = data.grade,
            name = data.name,
            number = data.number,
            profileImg = data.profileImg,
            uuid = data.uuid
        )
    }

    fun mapperToGetProfileImageData(data: GetProfileImageResponse): GetProfileImageData {
        return GetProfileImageData(
            profileImg = data.profileImg
        )
    }
}