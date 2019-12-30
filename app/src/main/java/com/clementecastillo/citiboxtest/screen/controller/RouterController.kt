package com.clementecastillo.citiboxtest.screen.controller

import com.clementecastillo.citiboxtestcore.domain.data.Post

interface RouterController {
    fun routeToLanding()
    fun routeToPostList()
    fun sharePost(post: Post)
}