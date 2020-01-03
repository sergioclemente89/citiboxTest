package com.clementecastillo.citiboxtest.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.clementecastillo.citiboxtest.injection.component.ScreenComponent
import com.clementecastillo.citiboxtest.presenter.Presenter
import com.clementecastillo.citiboxtest.presenter.PresenterView
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment(), LifecycleDisposables {

    override var disposables: CompositeDisposable? = null

    private var presenter: Presenter<*>? = null

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        clearComposite()
        presenter?.clear()
        super.onDestroyView()
    }

    fun screen(): ScreenComponent {
        return (activity as BaseActivity).screenComponent
    }
}