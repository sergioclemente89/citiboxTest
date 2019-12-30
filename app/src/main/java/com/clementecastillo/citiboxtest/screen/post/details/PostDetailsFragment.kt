package com.clementecastillo.citiboxtest.screen.post.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.client.ImageLoader
import com.clementecastillo.citiboxtest.extension.toPx
import com.clementecastillo.citiboxtest.screen.base.BaseFragment
import com.clementecastillo.citiboxtest.screen.controller.ToolbarController
import com.clementecastillo.citiboxtestcore.domain.data.PostDetails
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.content_post_details.*
import kotlinx.android.synthetic.main.post_details_layout.*
import javax.inject.Inject

class PostDetailsFragment : BaseFragment(), PostDetailsView {

    @Inject
    lateinit var presenter: PostDetailsPresenter
    @Inject
    lateinit var toolbarController: ToolbarController

    private val onUserInfoClickSubject = PublishSubject.create<User>()

    companion object {
        fun newInstance() = PostDetailsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screen().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.post_details_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarController.showBackButton()
        init(presenter, this)
    }

    override fun bindPostDetails(postDetails: PostDetails) {
        ImageLoader.loadUserAvatar(postDetails.user.id, user_avatar)
        postDetails.user.let {
            post_author.text = context?.getString(R.string.by_author, it.fullName, it.nickname)
        }
        post_title.text = postDetails.post.title.capitalize()
        post_body.text = postDetails.post.body
        post_comments_count.text = getString(R.string.x_comments, postDetails.comments.size)
        post_comments_recyclerview.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CommentsAdapter(postDetails.comments)
            addItemDecoration(CommentsDecoration())
        }

        user_info_button.setOnClickListener { onUserInfoClickSubject.onNext(postDetails.user) }
    }

    override fun onUserInfoButtonClick(): Observable<User> {
        return onUserInfoClickSubject
    }

    inner class CommentsDecoration : RecyclerView.ItemDecoration() {

        private val paint: Paint = Paint().apply {
            color = ContextCompat.getColor(requireContext(), R.color.separator_color)
            strokeWidth = 1.toPx().toFloat()
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(child)

                if (position < parent.adapter!!.itemCount - 1) {
                    val y = child.bottom.toFloat()
                    c.drawLine(0f, y, parent.width.toFloat(), y, paint)
                }
            }
        }
    }
}