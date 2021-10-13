package com.example.retrofitlabusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.retrofitlabusers.dto.AuthResponse
import com.example.retrofitlabusers.dto.UserResponse
import com.example.retrofitlabusers.model.authRequest
import com.example.retrofitlabusers.network.ApiAdapter
import com.example.retrofitlabusers.network.ApiAuthAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

private lateinit var tvUserName:EditText
private lateinit var tvPassword:EditText
private lateinit var cardLogin:CardView
private lateinit var loginModal:ConstraintLayout

class LoginActivity : AppCompatActivity(),CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvUserName= findViewById(R.id.etUserName)
        tvPassword= findViewById(R.id.etPassword)
        cardLogin = findViewById(R.id.cardLogin)
        cardLogin.setOnClickListener{login()}
        loginModal= findViewById(R.id.LoginModal)

    }

    fun login()
    {
        if(tvUserName.text.isEmpty() || tvPassword.text.isEmpty())
        {
            Toast.makeText(this,"Debe ingresar Usuario y Contraseña",Toast.LENGTH_SHORT).show()
            return
        }
        val request = authRequest(tvUserName.text.toString(), tvPassword.text.toString())
        loginModal.visibility=View.VISIBLE
        launch()
        {
            try {
                val apiResponse = ApiAuthAdapter.apiAuthClient.authenticate(request)
                if (apiResponse.isSuccessful && apiResponse.body() != null) {
                    val userResponse = apiResponse.body() as AuthResponse
                    //Log.v("APIDATA", "Data: ${userResponse.Succeeded}")
                    if(userResponse.Succeeded){
                        loginModal.visibility=View.GONE
                        initSession()
                    }
                } else {
                    Log.v("APIDATA", apiResponse.toString())
                    loginModal.visibility=View.GONE
                    Toast.makeText(this@LoginActivity,"Credenciales No Válidas",Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.v("APIDATA", "Exception Dev: ${e.localizedMessage}")
                Toast.makeText(this@LoginActivity,e.message,Toast.LENGTH_SHORT).show()
                loginModal.visibility=View.GONE
            }
        }
    }

    fun initSession(){
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}

