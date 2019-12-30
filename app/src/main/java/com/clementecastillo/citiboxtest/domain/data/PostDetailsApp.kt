package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.Comment
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.clementecastillo.citiboxtestcore.domain.data.User

class PostDetailsApp(override val post: Post, override var user: User, override var comments: List<Comment> = listOf()) : PostDetails