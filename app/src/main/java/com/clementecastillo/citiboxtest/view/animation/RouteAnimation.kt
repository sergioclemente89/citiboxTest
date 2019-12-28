package com.clementecastillo.citiboxtest.view.animation

import com.clementecastillo.citiboxtest.R

abstract class RouteAnimation {

    abstract val enter: Int
    abstract val exit: Int
    abstract val popEnter: Int
    abstract val popExit: Int

    companion object {
        val FADE: RouteAnimation = object : RouteAnimation() {
            override val enter: Int = android.R.anim.fade_in
            override val exit: Int = android.R.anim.fade_out
            override val popEnter: Int = R.anim.slide_in_left
            override val popExit: Int = R.anim.slide_out_right
        }

        val TRANSLATION: RouteAnimation = object : RouteAnimation() {
            override val enter: Int = R.anim.slide_in_right
            override val exit: Int = R.anim.slide_out_left
            override val popEnter: Int = R.anim.slide_in_left
            override val popExit: Int = R.anim.slide_out_right
        }

        val NOTHING: RouteAnimation = object : RouteAnimation() {
            override val enter: Int = 0
            override val exit: Int = 0
            override val popEnter: Int = 0
            override val popExit: Int = 0
        }
    }
}