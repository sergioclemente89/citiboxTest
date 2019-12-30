package com.clementecastillo.citiboxtest.screen.post.list

import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.extension.mainThread
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import com.clementecastillo.citiboxtest.usecase.GetMorePostsUseCase
import com.clementecastillo.citiboxtest.usecase.GetPostsUseCase
import com.clementecastillo.citiboxtestcore.transaction.unFold
import javax.inject.Inject

class PostListPresenter @Inject constructor(
    private val toolbarController: ToolbarController,
    private val loadingController: LoadingController,
    private val routerController: RouterController,
    private val getPostsUseCase: GetPostsUseCase,
    private val getMorePostsUseCase: GetMorePostsUseCase
) : Presenter<PostListView>() {

    override fun init(view: PostListView) {
        toolbarController.setScreenTitle(R.string.posts_list)

        view.run {
            getPostsUseCase.bind()
                .doOnSubscribe { loadingController.showLoading() }
                .doFinally { loadingController.hideLoading() }
                .mainThread()
                .subscribe { it ->
                    it.unFold(onSuccess = {
                        addPosts(it)
                    }, onError = {
                        // TODO: Show error
                    })
                }.toComposite()

            onRequestNextPage().subscribe {
                getMorePostsUseCase.bind(GetMorePostsUseCase.Params(it)).mainThread().subscribe { it ->
                    it.unFold(onSuccess = {
                        addPosts(it)
                    }, onError = {
                        // TODO: Show error
                    })
                }
            }.toComposite()

            onSharePostClick().subscribe {
                routerController.sharePost(it)
            }.toComposite()
        }

    }
}