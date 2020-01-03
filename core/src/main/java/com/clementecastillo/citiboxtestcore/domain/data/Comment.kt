package com.clementecastillo.citiboxtestcore.domain.data

interface Comment {
    val postId: Int
    val id: Int
    val title: String
    val email: String
    val body: String
}