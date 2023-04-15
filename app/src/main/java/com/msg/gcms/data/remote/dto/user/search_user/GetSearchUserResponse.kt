package com.msg.gcms.data.remote.dto.user.search_user

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import java.util.UUID

data class GetSearchUserResponse(
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("classNum")
    val classNum: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("profileImg")
    val profileImg: String?
)

fun GetSearchUserResponse.toGetSearchUserData(): GetSearchUserData {
    return GetSearchUserData(
        classNum = classNum,
        email = email,
        grade = grade,
        name = name,
        number = number,
        profileImg = profileImg,
        uuid = uuid
    )
}