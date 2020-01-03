package com.clementecastillo.citiboxtest

import android.content.Intent
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.splash.SplashActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19])
class SplashActivityTest {

    private lateinit var activity: SplashActivity

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(SplashActivity::class.java).setup().get()
    }

    @Test
    fun nextActivityWasLaunched() {
        val expectedIntent = Intent(activity, LandingActivity::class.java)
        val actualIntent = Shadows.shadowOf(activity).nextStartedActivity
        Assert.assertEquals(actualIntent.component, expectedIntent.component)
    }
}