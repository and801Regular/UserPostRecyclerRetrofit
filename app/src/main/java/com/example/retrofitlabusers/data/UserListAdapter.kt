package com.example.retrofitlabusers.data

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlabusers.R
import com.example.retrofitlabusers.dto.UserResponse
import com.example.retrofitlabusers.model.User
import javax.xml.transform.URIResolver

class UserListAdapter(private val users:ArrayList<User>, private val context: Context):
    RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    private var listener : RecyclerItemListener?=null
//    var mColors = arrayOf("#3F51B5", "#FF9800", "#009688", "#673AB7")

    var mColors = arrayOf("#1e88e5","#00897b","#512da8","#d84315")

    fun addListener(pListener: RecyclerItemListener){
        listener = pListener
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(user: User){

            val tvIdUser = itemView.findViewById(R.id.tvIdUser) as TextView
            val tvName = itemView.findViewById(R.id.tvName) as TextView
            val tvEmail = itemView.findViewById(R.id.tvEmail) as TextView
            val switch = itemView.findViewById(R.id.switchStatus) as Switch
            val checkBoxMale = itemView.findViewById(R.id.checkBoxMale) as CheckBox
            val checkBoxFemale = itemView.findViewById(R.id.checkBoxFemale) as CheckBox

            tvIdUser.text="ID: ${user.id.toString()}"
            tvName.text="Name: ${user.name.toString()}"
            tvEmail.text="Email: ${user.email.toString()}"
            switch.isChecked = user.status=="active"
            if(user.gender=="male") checkBoxMale.isChecked=true else checkBoxFemale.isChecked=true

            itemView.setOnClickListener{
                //Toast.makeText(context,country.text,Toast.LENGTH_SHORT).show()
                listener?.onItemClick(user)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.user_cardview,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<CardView>(R.id.cardviewUser).setCardBackgroundColor(Color.parseColor(mColors[position % 4])); // 4 can be replaced by mColors.length
        holder.bindItem(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface RecyclerItemListener{
        fun onItemClick(user: User)
    }


}