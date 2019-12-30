package com.clementecastillo.citiboxtest.screen.post.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.screen.base.BaseFragment
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import com.clementecastillo.citiboxtestcore.domain.data.Post
import io.reactivex.Observable
import kotlinx.android.synthetic.main.post_list_layout.*
import javax.inject.Inject

class PostListFragment : BaseFragment(), PostListView {

    @Inject
    lateinit var presenter: PostListPresenter
    @Inject
    lateinit var toolbarController: ToolbarController

    private val postsAdapter = PostsAdapter()

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
        toolbarController.setScreenTitle(R.string.posts_list)
        configureRecyclerView()
        updateEmptyView()
        init(presenter, this)
    }

    private fun configureRecyclerView() {
        posts_recyclerview.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postsAdapter
        }
    }

    override fun addPosts(postsList: List<Post>) {
        postsAdapter.addData(postsList)
        updateEmptyView()
    }

    override fun onPostClick(): Observable<Int> {
        return postsAdapter.onPostClick()
    }

    override fun onSharePostClick(): Observable<Post> {
        return postsAdapter.onSharePostClick()
    }

    override fun onRequestNextPage(): Observable<Int> {
        return postsAdapter.onNextPage()
    }

    private fun updateEmptyView() {
        if (postsAdapter.itemCount <= 1) {
            posts_empty_view.visibility = View.VISIBLE
            posts_recyclerview.visibility = View.INVISIBLE
        } else {
            posts_recyclerview.visibility = View.VISIBLE
            posts_empty_view.visibility = View.GONE
        }
    }

}