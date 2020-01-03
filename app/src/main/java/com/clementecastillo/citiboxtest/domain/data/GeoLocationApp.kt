package com.clementecastillo.citiboxtest.domain.data

import com.clementecastillo.citiboxtestcore.domain.data.GeoLocation

data class GeoLocationApp(override val lat: String, override val lng: String) : GeoLocation