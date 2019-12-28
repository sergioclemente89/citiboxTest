package com.clementecastillo.citiboxtest.extension

import androidx.fragment.app.Fragment

fun Fragment.tagName(): String {
    return javaClass.simpleName
}