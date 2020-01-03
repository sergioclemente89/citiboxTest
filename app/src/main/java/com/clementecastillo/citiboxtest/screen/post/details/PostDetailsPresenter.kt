package com.clementecastillo.citiboxtest.screen.post.details

import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.extension.mainThread
import com.clementecastillo.citiboxtest.extension.throttleDefault
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.screen.base.BaseDialogFragment
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
                    showError()
                } else {
                    getPostDetailsUseCase.bind(GetPostDetailsUseCase.Params(it.postId))
                        .doOnSubscribe { loadingController.showLoading() }
                        .doFinally { loadingController.hideLoading() }
                        .mainThread()
                        .subscribe { it ->
                            it.unFold(onSuccess = {
                                bindPostDetails(it)
                            }, onError = {
                                showError(R.string.get_post_details_error)
                            })
                        }.toComposite()
                }
            }

            onUserInfoButtonClick().throttleDefault().subscribe {
                routerController.showUserInfoDialog(it)
            }.toComposite()

            onShareButtonClick().throttleDefault().subscribe {
                routerController.sharePost(it)
            }.toComposite()
        }
    }

    private fun showError(messageErrorId: Int? = null) {
        routerController.showErrorDialogObservable(messageErrorId).subscribe {
            if (it == BaseDialogFragment.DialogStateEvent.DETACHED) {
                routerController.goBack()
            }
        }.toComposite()
    }
}