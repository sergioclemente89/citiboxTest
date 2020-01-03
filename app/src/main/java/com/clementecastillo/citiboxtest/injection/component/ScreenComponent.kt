package com.clementecastillo.citiboxtest.injection.component

import com.clementecastillo.citiboxtest.injection.ScreenScope
import com.clementecastillo.citiboxtest.injection.module.ScreenModule
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsFragment
import com.clementecastillo.citiboxtest.screen.post.list.PostListFragment
import com.clementecastillo.citiboxtest.screen.splash.SplashActivity
import com.clementecastillo.citiboxtest.screen.userinfo.UserInfoDialogFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class], modules = [ScreenModule::class])
interface ScreenComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(postListFragment: PostListFragment)
    fun inject(postDetailsFragment: PostDetailsFragment)
    fun inject(userInfoDialogFragment: UserInfoDialogFragment)
}