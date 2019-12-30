package com.clementecastillo.citiboxtest.screen.post.details

import com.clementecastillo.citiboxtest.extension.mainThread
import com.clementecastillo.citiboxtest.extension.throttleDefault
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.controller.LoadingController
import com.clementecastillo.citiboxtest.screen.controller.RouterController
import com.clementecastillo.citiboxtest.screen.resultstate.ResultStateLoader
import com.clementecastillo.citiboxtest.usecase.GetPostDetailsUseCase
import com.clementecastillo.citiboxtestcore.transaction.unFold
import javax.inject.Inject

class PostDetailsPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val resultStateLoader: ResultStateLoader,
    private val routerController: RouterController
) : Presenter<PostDetailsView>() {

    override fun init(view: PostDetailsView) {
        view.run {
            resultStateLoader.load<PostDetailsResultState>(PostDetailsView::class).let {
                if (it == null) {
                    // TODO: Show wrong routing error
                } else {
                    getPostDetailsUseCase.bind(GetPostDetailsUseCase.Params(it.postId))
                        .doOnSubscribe { loadingController.showLoading() }
                        .doFinally { loadingController.hideLoading() }
                        .mainThread()
                        .subscribe { it ->
                            it.unFold(onSuccess = {
                                bindPostDetails(it)
                            }, onError = {
                                // TODO: Show error
                            })
                        }
                }
            }

            onUserInfoButtonClick().throttleDefault().subscribe {
                routerController.showUserInfoDialog(it)
            }
        }
    }
}