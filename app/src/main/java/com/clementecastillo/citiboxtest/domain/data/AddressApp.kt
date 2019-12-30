package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.Address
import com.google.gson.annotations.SerializedName

data class AddressApp(
    override val street: String,
    override val suite: String,
    override val city: String,
    override val zipcode: String,
    @SerializedName("geo")
    override val geoLocation: GeoLocationApp
) : Address {

    override fun getFullAddress(): String {
        return "$street $suite, $city - $zipcode"
    }
}