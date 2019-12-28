package com.clementecastillo.citiboxtest.screen.landing

import android.os.Bundle
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.screen.base.BaseActivity

class LandingActivity : BaseActivity(), LandingView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_layout)

        if (currentFragment() == null) {
            routeToPostList()
        }
    }
}