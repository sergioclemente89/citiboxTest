package com.clementecastillo.citiboxtest.screen.post.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtestcore.domain.data.Comment
import kotlinx.android.synthetic.main.comment_itemview.view.*

class CommentsAdapter(private val commentsList: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_itemview, parent, false))
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bindPost(commentsList[position])
    }

    inner class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPost(comment: Comment) {
            itemView.run {
                comment_author.text = comment.email
                comment_title.text = comment.title
                comment_body.text = comment.body
            }
        }
    }
}