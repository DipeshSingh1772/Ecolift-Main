package com.example.ecolift.MainActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.example.ecolift.StartActivities.LoginActivity
import com.example.ecolift.databinding.ActivityProfileMenuBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileMenuActivity : AppCompatActivity() {

    lateinit var sessionManager:SessionManager
    private lateinit var binding: ActivityProfileMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)
        binding = ActivityProfileMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)


        sessionManager = SessionManager(this)
        val userName = sessionManager.fetchUserName()
        if(userName!=null && userName.isNotEmpty()){
            binding.userName.text = "Hi, $userName!"
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }
        binding.logoutProfileIcon.setOnClickListener {
            logout()
        }

    }

    fun logout(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val intent = Intent(this, LoginActivity::class.java)
        binding.progressBar.visibility = View.VISIBLE


        retrofitBuilder.logout(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    if(response.isSuccessful){
                        binding.progressBar.visibility = View.GONE
                        sessionManager.clearAllTokens()
                        startActivity(intent)
                        this@ProfileMenuActivity.finish()
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(this@ProfileMenuActivity, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(this@ProfileMenuActivity, "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })
    }
}