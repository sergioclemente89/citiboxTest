package com.clementecastillo.citiboxtest.screen.post.list

import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import javax.inject.Inject

class PostListPresenter @Inject constructor(
    private val toolbarController: ToolbarController,
    private val loadingController: LoadingController
) : Presenter<PostListView>() {

    override fun init(view: PostListView) {
        toolbarController.setScreenTitle(R.string.posts_list)
    }

}