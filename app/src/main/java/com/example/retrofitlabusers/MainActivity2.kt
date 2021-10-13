package com.example.retrofitlabusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlabusers.data.PostListAdapter
import com.example.retrofitlabusers.data.UserListAdapter
import com.example.retrofitlabusers.dto.PostResponse
import com.example.retrofitlabusers.dto.UserResponse
import com.example.retrofitlabusers.model.Post
import com.example.retrofitlabusers.model.User
import com.example.retrofitlabusers.network.ApiAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.lang.Exception

//private lateinit var tvIdUser:TextView
private lateinit var userPostRecycler:RecyclerView


private lateinit var postAdapter:PostListAdapter
private lateinit var layoutManager:RecyclerView.LayoutManager
private lateinit var postList:ArrayList<Post>
private lateinit var buttonReturn:FloatingActionButton
private lateinit var toolbar2:androidx.appcompat.widget.Toolbar
private lateinit var btnExit: Button


class MainActivity2 : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

    /* supportActionBar!!.title="${intent.getStringExtra("userName")} Posts"

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_action_posts);// set drawable icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
*/
        //tvIdUser=findViewById(R.id.tvIdUserParam)
        toolbar2 = findViewById(R.id.toolbar2)
        btnExit = toolbar2.findViewById(R.id.button)
        setSupportActionBar(toolbar2)


        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            elevation = 15F
            title = "${intent.getStringExtra("userName")} Posts"
            setIcon(R.drawable.ic_action_posts)

            // toolbar button click listener
            btnExit.setOnClickListener {
                // change toolbar title
                intent = Intent(this@MainActivity2,LoginActivity::class.java)
                startActivity(intent)
            }
        }


        userPostRecycler = findViewById(R.id.userPostRecycler)

        val idUser = intent.getStringExtra("idUser")
        //tvIdUser.text=idUser

        buttonReturn=findViewById(R.id.floatingActionButtonBack)
        buttonReturn.setOnClickListener { onReturnClick() }

        postList = ArrayList()
        layoutManager = LinearLayoutManager(this)

        userPostRecycler = findViewById(R.id.userPostRecycler)
        userPostRecycler.layoutManager= layoutManager
        postAdapter = PostListAdapter(postList,this)
        userPostRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        loadData(idUser!!.toString())

    }

    private fun loadData(id:String){

        launch()
        {
            try {
                val apiResponse = ApiAdapter.apiClient.getUserPosts(id)
                if (apiResponse.isSuccessful && apiResponse.body() != null) {
                    val postResponse = apiResponse.body() as PostResponse
                    initUserRecycler(postResponse)
                    Log.v("APIDATA", "Data: ${postResponse.data}")
                } else {
                    Log.v("APIDATA", "No se encontro esa raza")
                }
            } catch (e: Exception) {
                Log.v("APIDATA", "Exception Dev: ${e.localizedMessage}")
            }

        }


    }

    private fun initUserRecycler( postResponse: PostResponse){
        if(postResponse.meta!!.pagination.total>0){
            postList = postResponse.data!!
        }
        else{
            val toast = Toast.makeText(this,"El usuario no tiene Post",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }


        postAdapter = PostListAdapter(postList,this)
        //userAdapter!!.addListener(this)
        userPostRecycler.adapter= postAdapter
    }

    private fun onReturnClick(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}