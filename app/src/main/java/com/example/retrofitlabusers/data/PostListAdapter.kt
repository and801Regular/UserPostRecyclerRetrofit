package com.example.retrofitlabusers.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlabusers.R
import com.example.retrofitlabusers.model.Post
import com.example.retrofitlabusers.model.User
import org.w3c.dom.Text

class PostListAdapter(private val posts:ArrayList<Post>, private val context: Context):
    RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    private var listener : RecyclerItemListener?=null

    fun addListener(pListener: RecyclerItemListener){
        listener = pListener
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(post: Post){

            itemView.setOnClickListener{
                //Toast.makeText(context,country.text,Toast.LENGTH_SHORT).show()
                listener?.onItemClick(post)
            }

            val tvIdPost = itemView.findViewById(R.id.tvPostId) as TextView
            val tvPostTitle = itemView.findViewById(R.id.tvTitle) as TextView
            val tvPostBody = itemView.findViewById(R.id.tvBody) as TextView

            tvIdPost.text= "ID: ${post.id.toString()}"
            tvPostTitle.text = "Title: ${post.title}"
            tvPostBody.text = "Body: ${post.body}"

            tvPostTitle.setOnClickListener {
                if(tvIdPost.visibility==View.GONE) tvIdPost.visibility=View.VISIBLE else tvIdPost.visibility=View.GONE
                if(tvPostBody.visibility==View.GONE) tvPostBody.visibility=View.VISIBLE else tvPostBody.visibility=View.GONE
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.post_cardview,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    interface RecyclerItemListener{
        fun onItemClick(post: Post)
    }


}