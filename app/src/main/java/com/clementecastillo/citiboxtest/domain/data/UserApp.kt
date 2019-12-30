package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.User
import com.google.gson.annotations.SerializedName

data class UserApp(
    override val id: Int,
    @SerializedName("name")
    override val fullName: String,
    @SerializedName("username")
    override val nickname: String,
    override val email: String,
    override val phone: String,
    override val website: String
) : User