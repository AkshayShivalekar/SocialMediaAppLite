package com.example.socialapp2.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp2.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener:IPostAdapter ) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage:ImageView = itemView.findViewById(R.id.userImage)
        val userText:TextView = itemView.findViewById(R.id.userName)
        val postText:TextView =itemView.findViewById(R.id.postTitle)
        val createdAt:TextView = itemView.findViewById(R.id.createdAt)
        val btnLike:ImageView = itemView.findViewById(R.id.btnLike)
        val likeCount:TextView = itemView.findViewById(R.id.tvLikeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent,false))
        viewHolder.btnLike.setOnClickListener {
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)

        if (isLiked){
            holder.btnLike.setImageDrawable(ContextCompat.getDrawable(holder.btnLike.context, R.drawable.ic_liked))
        }else{
            holder.btnLike.setImageDrawable(ContextCompat.getDrawable(holder.btnLike.context, R.drawable.ic_unliked))
        }
    }

    interface IPostAdapter{
        fun onLikeClicked(postId:String)
    }
}