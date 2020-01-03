package com.clementecastillo.citiboxtest

import com.clementecastillo.citiboxtest.domain.data.PostApp
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.list.PostListFragment
import kotlinx.android.synthetic.main.post_list_layout.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19])
class PostListTest {

    private lateinit var activity: LandingActivity
    private lateinit var postListFragment: PostListFragment
    private val postListTest = listOf(
        PostApp(1, 1, "PostTitle1", "PostBody1", null),
        PostApp(2, 1, "PostTitle2", "PostBody2", null),
        PostApp(3, 1, "PostTitle3", "PostBody3", null),
        PostApp(4, 1, "PostTitle4", "PostBody4", null),
        PostApp(5, 1, "PostTitle5", "PostBody5", null)
    )

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(LandingActivity::class.java).setup().get()
        activity.routeToPostList()
        postListFragment = activity.currentFragment() as PostListFragment
    }
    
    @Test
    fun postListIsBinding() {
        postListFragment.run {
            setPosts(postListTest)
            assert(posts_recyclerview.adapter!!.itemCount == 5)
        }
    }

    @Test
    fun postListTitle() {
        assertEquals(activity.getScreenTitle(), activity.getString(R.string.posts_list))
    }
}