package com.clementecastillo.citiboxtest.screen.post.list

import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.client.data.OrderPost
import com.clementecastillo.citiboxtest.client.data.SortPost
import com.clementecastillo.citiboxtest.extension.mainThread
import com.clementecastillo.citiboxtest.extension.throttleDefault
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import com.clementecastillo.citiboxtest.usecase.GetPostsUseCase
import com.clementecastillo.citiboxtestcore.transaction.unFold
import javax.inject.Inject

class PostListPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val routerController: RouterController,
    private val getPostsUseCase: GetPostsUseCase,
    private val toolbarController: ToolbarController
) : Presenter<PostListView>() {

    private var currentSortPost = SortPost.ID
    private var currentOrderPost = OrderPost.ASC

    override fun init(view: PostListView) {
        view.run {
            setToolbar(this)
            onRequestNextPage().subscribe { currentCount ->
                getPosts(view, currentCount, false)
            }.toComposite()

            onSharePostClick().throttleDefault().subscribe {
                routerController.sharePost(it)
            }.toComposite()

            onPostClick().throttleDefault().subscribe {
                routerController.routeToPostDetails(it)
            }.toComposite()

            onRefreshList().throttleDefault().subscribe {
                getPosts(view, 0, true)
            }
        }
    }

    private fun setToolbar(view: PostListView) {
        toolbarController.run {
            setScreenTitle(R.string.posts_list)
            setToolbarMenu(R.menu.posts_list_menu).subscribe {
                when (it) {
                    R.id.order_asc_action -> {
                        if (currentOrderPost != OrderPost.ASC) {
                            currentOrderPost = OrderPost.ASC
                            getPosts(view, 0, true)
                        }
                    }
                    R.id.order_desc_action -> {
                        if (currentOrderPost != OrderPost.DESC) {
                            currentOrderPost = OrderPost.DESC
                            getPosts(view, 0, true)
                        }
                    }
                    R.id.sort_id_action -> {
                        if (currentSortPost != SortPost.ID) {
                            currentSortPost = SortPost.ID
                            getPosts(view, 0, true)
                        }
                    }
                    R.id.sort_user_action -> {
                        if (currentSortPost != SortPost.USER) {
                            currentSortPost = SortPost.USER
                            getPosts(view, 0, true)
                        }
                    }
                    R.id.sort_title_action -> {
                        if (currentSortPost != SortPost.TITLE) {
                            currentSortPost = SortPost.TITLE
                            getPosts(view, 0, true)
                        }
                    }
                    R.id.sort_body_action -> {
                        if (currentSortPost != SortPost.BODY) {
                            currentSortPost = SortPost.BODY
                            getPosts(view, 0, true)
                        }
                    }
                }
            }.toComposite()
        }
    }

    private fun getPosts(view: PostListView, currentItemCount: Int, ignoreCache: Boolean) {
        getPostsUseCase.bind(GetPostsUseCase.Params(currentItemCount, currentSortPost, currentOrderPost, ignoreCache))
            .doOnSubscribe {
                if (currentItemCount == 0) {
                    loadingController.showLoading()
                }
            }
            .doFinally { loadingController.hideLoading() }
            .mainThread().subscribe { it ->
                it.unFold(onSuccess = {
                    if (ignoreCache) {
                        view.setPosts(it)
                    } else {
                        view.addPosts(it)
                    }
                }, onError = {
                    routerController.showErrorDialogObservable().subscribe()
                })
            }.toComposite()
    }
}