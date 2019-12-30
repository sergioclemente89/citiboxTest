package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.Comment
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.User

data class PostApp(
    override val id: Int,
    override val userId: Int,
    override val title: String,
    override val body: String,
    override var user: User?,
    override var comments: List<Comment> = listOf()
) : Post