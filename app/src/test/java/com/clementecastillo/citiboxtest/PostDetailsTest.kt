package com.clementecastillo.citiboxtest

import com.clementecastillo.citiboxtest.domain.data.*
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsFragment
import kotlinx.android.synthetic.main.content_post_details.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19])
class PostDetailsTest {

    private lateinit var activity: LandingActivity
    private lateinit var postDetailsFragment: PostDetailsFragment
    private val userTest = UserApp(
        1, "FullName", "Nickname", "email@test.com",
        AddressApp("Street", "Suite", "City", "Zipcode", GeoLocationApp("0", "0")),
        "Phone", "Website"
    )
    private val postDetailsTest: PostDetailsApp = PostDetailsApp(
        PostApp(
            1, 1, "PostTitle", "PostBody",
            userTest
        ),
        userTest,
        listOf()
    )

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(LandingActivity::class.java).setup().get()
        activity.routeToPostDetails(-1)
        postDetailsFragment = activity.currentFragment() as PostDetailsFragment
    }

    @Test
    fun backButtonIsShowingAtToolbar() {
        assertTrue(activity.getToolbarView()?.navigationIcon != null)
    }

    @Test
    fun postListTitle() {
        assertTrue(activity.getScreenTitle().isNullOrEmpty())
    }

    @Test
    fun postDetailsIsBinded() {
        postDetailsFragment.run {
            bindPostDetails(postDetailsTest)
            assertEquals(post_title.text, "PostTitle")
            assertEquals(post_body.text, "PostBody")
            assertEquals(post_comments_count.text, getString(R.string.x_comments, 0))
        }
    }
}