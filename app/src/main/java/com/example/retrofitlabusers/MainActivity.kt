package com.example.retrofitlabusers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlabusers.data.UserListAdapter
import com.example.retrofitlabusers.dto.UserResponse
import com.example.retrofitlabusers.model.User
import com.example.retrofitlabusers.network.ApiAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

private lateinit var userRecycler: RecyclerView
private lateinit var userAdapter:UserListAdapter
private lateinit var layoutManager:RecyclerView.LayoutManager
private lateinit var userList:ArrayList<User>
private lateinit var progress:ProgressBar
private lateinit var btnLog: Button
private lateinit var btnNextPage: FloatingActionButton
private lateinit var btnPreviousPage: FloatingActionButton
private lateinit var tvPager:TextView
private lateinit var toolbar:androidx.appcompat.widget.Toolbar
private lateinit var btnExit: Button

private var pageNumber:Int = 1

class MainActivity : AppCompatActivity(), UserListAdapter.RecyclerItemListener, CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_action_user);// set drawable icon
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        toolbar = findViewById(R.id.toolbar)
        btnExit = findViewById(R.id.button)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Users"
            elevation = 15F
            setIcon(R.drawable.ic_action_user)
            //setDisplayHomeAsUpEnabled(true)

            // toolbar button click listener
            btnExit.setOnClickListener {
                // change toolbar title
                intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
            }
        }



        progress = findViewById(R.id.progressBar)
        progress.visibility=View.GONE

        btnNextPage=findViewById(R.id.floatingActionButtonNext)
        btnPreviousPage=findViewById(R.id.floatingActionButtonPrev)
        tvPager = findViewById(R.id.tvPager)

        btnNextPage.setOnClickListener { pageNumber++;loadData()   }
        btnPreviousPage.setOnClickListener { pageNumber--;loadData() }


        userList = ArrayList()
        layoutManager = LinearLayoutManager(this)


        userRecycler = findViewById(R.id.usersRecycler)
        userRecycler.layoutManager= layoutManager
        userAdapter = UserListAdapter(userList,this)
        //userRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        userAdapter!!.addListener(this)
        loadData()
    }

    override fun onItemClick(user: User) {
       val intent= Intent(this,MainActivity2::class.java)
        intent.putExtra("idUser",user.id.toString())
        intent.putExtra("userName",user.name.toString())
        startActivity(intent)
    }

    private fun loadData(){
        progress.visibility= View.VISIBLE
        launch()
        {
            try {
               //val apiResponse = ApiAdapter.apiClient.getListOfUsers("https://gorest.co.in/public/v1/users?page=$pageNumber")
                val apiResponse = ApiAdapter.apiClient.getListOfUsers(pageNumber)
                if (apiResponse.isSuccessful && apiResponse.body() != null) {
                    val userResponse = apiResponse.body() as UserResponse
                    initUserRecycler(userResponse)
                    Log.v("APIDATA", "Data: ${userResponse.data}")
                } else {
                    Log.v("APIDATA", "No se encontraron datos")
                }
            } catch (e: Exception) {
                Log.v("APIDATA", "Exception Dev: ${e.localizedMessage}")
                progress.visibility= View.GONE
            }
        }
        progress.visibility= View.GONE


    }

    private fun initUserRecycler( userResponse: UserResponse){
        if(userResponse.meta!!.pagination.total>0){
            userList = userResponse.data!!
        }
        Log.v("APIDATA", userList[0].id.toString())

        tvPager.text = "${userResponse.meta!!.pagination.page} de ${userResponse.meta!!.pagination.pages}"

        btnNextPage.visibility = if(userResponse.meta!!.pagination.links.next!=null) View.VISIBLE else View.GONE
        btnPreviousPage.visibility= if(userResponse.meta!!.pagination.links.previous!=null) View.VISIBLE else View.GONE

        userAdapter = UserListAdapter(userList,this)
        userAdapter!!.addListener(this)
        userRecycler.adapter= userAdapter
    }
}