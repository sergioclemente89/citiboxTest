package com.clementecastillo.citiboxtest

import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsView
import com.clementecastillo.citiboxtest.screen.post.list.PostListView
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19])
class LandingActivityTest {

    private lateinit var activity: LandingActivity

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(LandingActivity::class.java).create().resume().get()
    }

    @Test
    fun canRouteToPostDetails() {
        activity.routeToPostDetails(1)
        assertTrue(activity.currentFragment() is PostDetailsView)
    }

    @Test
    fun canRouteToPostList() {
        activity.routeToPostList()
        assertTrue(activity.currentFragment() is PostListView)
    }

}