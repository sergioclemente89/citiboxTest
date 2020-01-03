package com.clementecastillo.citiboxtestcore.domain.data

interface Address {
    val street: String
    val suite: String
    val city: String
    val zipcode: String
    val geoLocation: GeoLocation

    fun getFullAddress(): String
}