package com.example.ecolift.StartActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import android.view.View
import android.widget.*
import com.example.ecolift.BookRideActivities.BookActivity
import com.example.ecolift.Data_Classes.LoginRequest
import com.example.ecolift.Data_Classes.LoginResponse
import com.example.ecolift.MainActivities.MainActivity
import com.example.ecolift.MainActivities.MainFragment
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.example.ecolift.viewModels.EcoliftViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(12,104,55)

        val login_btn = findViewById<Button>(R.id.login_btn)
        val create_btn = findViewById<Button>(R.id.create_btn)
        val forget_btn = findViewById<TextView>(R.id.forget_pass_btn)


        create_btn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            this.finish()
        }

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        if (token != null && token.isNotEmpty()) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {


            login_btn.setOnClickListener {
                Login()
            }

            forget_btn.setOnClickListener{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }


    }

    private fun Login() {

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder

        val emailEditText = findViewById<TextInputEditText>(R.id.login_email_btn)
        val passwordEditText = findViewById<TextInputEditText>(R.id.login_password_btn)
        val progressBar = findViewById<ProgressBar>(R.id.login_progress_bar)
        progressBar.visibility = View.VISIBLE

        if (isEntryValid()) {

            val intent = Intent(this, MainActivity::class.java)
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            retrofitBuilder.login(LoginRequest(email = email, password = password)).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if(response.isSuccessful){

                        val loginResponse = response.body()!!
                        val myStringBuilder = StringBuilder()
                        progressBar.visibility = View.GONE
                        sessionManager.saveAuthToken(loginResponse.authToken)
                        Toast.makeText(this@LoginActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    }
                    else{
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Wrong Credential", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("unsucces full login", t.toString())
                    Toast.makeText(this@LoginActivity, "Connection Problem", Toast.LENGTH_LONG).show()

                }

            })
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Fill Details Properly", Toast.LENGTH_SHORT).show()
        }

    }


    // checking that is any form content is blank or not. if blank return false or true
    private fun isEntryValid(): Boolean {

        val emailEditText = findViewById<TextInputEditText>(R.id.login_email_btn)
        val passwordEditText = findViewById<TextInputEditText>(R.id.login_password_btn)

        return !(emailEditText.text!!.isBlank()
                || passwordEditText.text!!.isBlank())

    }


}