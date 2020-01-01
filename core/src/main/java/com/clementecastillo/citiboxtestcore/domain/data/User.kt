package com.clementecastillo.citiboxtestcore.domain.data

interface User {
    val id: Int
    val fullName: String
    val nickname: String
    val email: String
    val address: Address
    val phone: String
    val website: String
}