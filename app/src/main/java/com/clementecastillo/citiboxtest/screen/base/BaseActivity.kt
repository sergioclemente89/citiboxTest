package com.clementecastillo.citiboxtest.screen.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.clementecastillo.citiboxtest.CitiboxTestApp
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
import com.clementecastillo.citiboxtest.screen.errordialog.ErrorDialogFragment
import com.clementecastillo.citiboxtest.screen.landing.LandingActivity
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsFragment
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsResultState
import com.clementecastillo.citiboxtest.screen.post.details.PostDetailsView
import com.clementecastillo.citiboxtest.screen.post.list.PostListFragment
import com.clementecastillo.citiboxtest.screen.userinfo.UserInfoDialogFragment
import com.clementecastillo.citiboxtest.screen.userinfo.UserInfoDialogView
import com.clementecastillo.citiboxtest.screen.userinfo.UserInfoResultState
import com.clementecastillo.citiboxtest.view.SystemBarTintManager
import com.clementecastillo.citiboxtest.view.animation.RouteAnimation
import com.clementecastillo.citiboxtestcore.domain.data.Post
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable
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
    private val resultStateSaver by lazy { CitiboxTestApp.appController.appComponent.provideResultStateSaver() }
    open val tintStatusBar = true

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemBarTintManager(this).apply {
            isStatusBarTintEnabled = tintStatusBar
            setTintColor(ContextCompat.getColor(applicationContext, R.color.statusbar_color))
        }
    }

    override fun onDestroy() {
        presenter?.clear()
        super.onDestroy()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun routeToLanding() {
        routeTo(LandingActivity::class.java, true, RouteAnimation.FADE, null, false)
    }

    override fun routeToPostList() {
        routeTo(PostListFragment.newInstance(), false, RouteAnimation.NOTHING)
    }

    override fun routeToPostDetails(postId: Int) {
        resultStateSaver.save(PostDetailsView::class, PostDetailsResultState(postId))
        routeTo(PostDetailsFragment.newInstance(), true, RouteAnimation.TRANSLATION)
    }

    override fun showUserInfoDialog(user: User) {
        resultStateSaver.save(UserInfoDialogView::class, UserInfoResultState(user))
        UserInfoDialogFragment.create().show(supportFragmentManager)
    }

    override fun sharePost(post: Post) {
        fun getShareContent(post: Post): String {
            return "${post.title}\n\n${post.body}"
        }

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_post_subject))
            putExtra(Intent.EXTRA_TEXT, getShareContent(post))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }

    override fun openCaller(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    override fun openNavigation(latitude: String, longitude: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=$latitude,$longitude")
        }
        startActivity(intent)
    }

    override fun showErrorDialogObservable(messageRes: Int?): Observable<BaseDialogFragment.DialogStateEvent> {
        val dialog = if (messageRes == null) {
            ErrorDialogFragment.default()
        } else {
            ErrorDialogFragment.create(messageRes)
        }
        return dialog.show(supportFragmentManager).events()
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

    override fun showBackButton() {
        getToolbarView()?.run {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setToolbarMenu(menu: Int): Observable<Int> {
        return Observable.create<Int> { emitter ->
            getToolbarView()?.apply {
                inflateMenu(menu)
                setOnMenuItemClickListener {
                    emitter.onNext(it.itemId)
                    true
                }
            }
        }
    }

    override fun clearToolbarMenu() {
        getToolbarView()?.menu?.clear()
    }

    private fun getToolbarView(): Toolbar? {
        return findViewById(R.id.toolbar_view)
    }

}