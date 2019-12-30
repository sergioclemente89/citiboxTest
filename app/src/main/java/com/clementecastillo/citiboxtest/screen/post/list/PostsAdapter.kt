package com.clementecastillo.citiboxtest.screen.post.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.client.api.ApiClientImpl
import com.clementecastillo.citiboxtestcore.domain.data.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.post_itemview.view.*

class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val POST_VIEWTYPE = 0
        private const val PAGER_VIEWTYPE = 1
    }

    private val postsList: MutableList<Post> = mutableListOf()
    private val nextPageSubject = PublishSubject.create<Int>()
    private val onPostClickSubject = PublishSubject.create<Int>()
    private val onSharePostClickSubject = PublishSubject.create<Post>()
    private var canLoadMore = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            POST_VIEWTYPE -> PostHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_itemview, parent, false))
            PAGER_VIEWTYPE -> PagerHolder(LayoutInflater.from(parent.context).inflate(R.layout.pager_item_view, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> if (canLoadMore) PAGER_VIEWTYPE else POST_VIEWTYPE
            else -> POST_VIEWTYPE
        }
    }

    override fun getItemCount(): Int {
        return postsList.size + if (canLoadMore) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostHolder -> holder.bindPost(postsList[position])
            is PagerHolder -> holder.bind()
        }
    }

    fun addData(newPostsList: List<Post>) {
        canLoadMore = newPostsList.size >= ApiClientImpl.POST_PAGE_ITEM_COUNT
        postsList.addAll(newPostsList)
        notifyDataSetChanged()
    }

    fun onNextPage(): Observable<Int> = nextPageSubject
    fun onPostClick(): Observable<Int> = onPostClickSubject
    fun onSharePostClick(): Observable<Post> = onSharePostClickSubject

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPost(currentPost: Post) {
            itemView.run {
                post_title.text = currentPost.title.capitalize()
                post_body.text = currentPost.body
                post_share_button.setOnClickListener {
                    onSharePostClickSubject.onNext(currentPost)
                }
                post_cardview.setOnClickListener {
                    onPostClickSubject.onNext(currentPost.id)
                }
            }
        }
    }

    inner class PagerHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            nextPageSubject.onNext(postsList.size)
        }
    }
}