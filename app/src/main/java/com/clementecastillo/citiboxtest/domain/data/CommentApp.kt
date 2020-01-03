package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.Comment
import com.google.gson.annotations.SerializedName

data class CommentApp(
    override val postId: Int,
    override val id: Int,
    @SerializedName("name")
    override val title: String,
    override val email: String,
    override val body: String
) : Comment