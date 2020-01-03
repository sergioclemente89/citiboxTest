package com.clementecastillo.citiboxtestcore.domain.data

interface Post {
    val id: Int
    val userId: Int
    val title: String
    val body: String
    var user: User?
}