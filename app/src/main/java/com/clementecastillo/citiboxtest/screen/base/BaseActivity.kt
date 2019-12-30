package com.clementecastillo.citiboxtest.screen.base

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.extension.fadeIn
import com.clementecastillo.citiboxtest.extension.fadeOut
import com.clementecastillo.citiboxtest.extension.hideKeyboard
import com.clementecastillo.citiboxtest.extension.tagName
import com.clementecastillo.citiboxtest.injection.component.DaggerScreenComponent
import com.clementecastillo.citiboxtest.injection.component.ScreenComponent
import com.clementecastillo.citiboxtest.injection.controller.AppController
import com.clementecastillo.citiboxtest.injection.controller.ScreenController
import com.clementecastillo.citiboxtest.injection.module.ScreenModule
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.presenter.PresenterView
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.list.PostListFragment
import com.clementecastillo.citiboxtest.view.animation.RouteAnimation
import com.clementecastillo.citiboxtestcore.domain.data.Post
import kotlinx.android.synthetic.main.loading_layout.*

private const val FRAME_CONTAINER_ID = R.id.frame_container

@SuppressLint("Registered")
open class BaseActivity : LifecycleActivity(), ScreenController, RouterController, LoadingController, ToolbarController {

    override val screenComponent: ScreenComponent by lazy {
        DaggerScreenComponent.builder()
            .appComponent((applicationContext as AppController).appComponent)
            .screenModule(ScreenModule(this))
            .build()
    }

    private var presenter: Presenter<*>? = null

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onDestroy() {
        presenter?.clear()
        super.onDestroy()
    }

    override fun routeToLanding() {
        routeTo(LandingActivity::class.java, true, RouteAnimation.FADE, null, false)
    }

    override fun routeToPostList() {
        routeTo(PostListFragment.newInstance(), false, RouteAnimation.NOTHING)
    }

    override fun sharePost(post: Post) {
        // TODO: Share post with intent chooser
    }


    fun currentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(FRAME_CONTAINER_ID) as BaseFragment?
    }

    private fun routeTo(activityClass: Class<out BaseActivity>, clearTask: Boolean, animation: RouteAnimation?, extras: Map<String, String>?, bringToFront: Boolean) {
        val intent = Intent(this, activityClass)
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        if (bringToFront) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }

        extras?.forEach {
            intent.putExtra(it.key, it.value)
        }

        onAlive().subscribe {
            if (animation == RouteAnimation.NOTHING) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
            startActivity(intent)
            if (animation != null) {
                overridePendingTransition(animation.enter, animation.exit)
            }
        }.toComposite()
    }

    private fun routeTo(fragment: BaseFragment, stack: Boolean, animation: RouteAnimation? = null, containerId: Int? = FRAME_CONTAINER_ID) {
        hideKeyboard()
        hideLoading()
        onAlive()
            .subscribe {
                val transaction = supportFragmentManager.beginTransaction()
                if (animation != null) {
                    transaction.setCustomAnimations(
                        if (currentFragment() == null) RouteAnimation.FADE.enter else animation.enter,
                        animation.exit,
                        animation.popEnter,
                        animation.popExit
                    )
                }
                if (stack) {
                    transaction.addToBackStack(fragment.tagName())
                }
                transaction.replace(containerId ?: FRAME_CONTAINER_ID, fragment)
                try {
                    transaction.commitAllowingStateLoss()
                } catch (e: Exception) {
                    // this should not be necessary
                }
            }.toComposite()
    }


    override fun showLoading() {
        runOnUiThread {
            loading_view?.fadeIn()
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            loading_view?.fadeOut()
        }
    }

    override fun setScreenTitle(titleResId: Int) {
        getToolbarView()?.findViewById<AppCompatTextView>(R.id.toolbar_title)?.setText(titleResId)
    }

    private fun getToolbarView(): Toolbar? {
        return findViewById(R.id.toolbar_view)
    }

}