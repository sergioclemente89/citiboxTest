package com.clementecastillo.citiboxtestcore.domain.data

interface PostDetails {
    val post: Post
    val user: User
    val comments: List<Comment>
}