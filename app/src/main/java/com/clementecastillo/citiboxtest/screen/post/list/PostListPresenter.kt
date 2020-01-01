package com.clementecastillo.citiboxtest.screen.post.list

import com.clementecastillo.citiboxtest.extension.mainThread
import com.clementecastillo.citiboxtest.extension.throttleDefault
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.usecase.GetPostsUseCase
import com.clementecastillo.citiboxtestcore.transaction.unFold
import javax.inject.Inject

class PostListPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val routerController: RouterController,
    private val getPostsUseCase: GetPostsUseCase
) : Presenter<PostListView>() {

    override fun init(view: PostListView) {
        view.run {
            onRequestNextPage()
                .subscribe { currentCount ->
                    getPostsUseCase.bind(GetPostsUseCase.Params(currentCount))
                        .doOnSubscribe {
                            if (currentCount == 0) {
                                loadingController.showLoading()
                            }
                        }
                        .doFinally { loadingController.hideLoading() }
                        .mainThread().subscribe { it ->
                            it.unFold(onSuccess = {
                                addPosts(it)
                            }, onError = {
                                routerController.showErrorDialogObservable().subscribe()
                            })
                        }
                }.toComposite()

            onSharePostClick().throttleDefault().subscribe {
                routerController.sharePost(it)
            }.toComposite()

            onPostClick().throttleDefault().subscribe {
                routerController.routeToPostDetails(it)
            }.toComposite()
        }

    }
}