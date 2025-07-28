package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class TeamPlayer(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("modified_at")
    val modifiedAt: String?
)
