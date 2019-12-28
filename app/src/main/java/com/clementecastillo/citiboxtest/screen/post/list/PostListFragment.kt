package com.clementecastillo.citiboxtest.screen.post.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.screen.base.BaseFragment
import javax.inject.Inject

class PostListFragment : BaseFragment(), PostListView {

    @Inject
    lateinit var presenter: PostListPresenter

    companion object {
        fun newInstance() = PostListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screen().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.post_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(presenter, this)
    }
}