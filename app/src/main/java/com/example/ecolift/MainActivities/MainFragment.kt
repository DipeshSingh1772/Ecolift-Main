package com.example.ecolift.MainActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ecolift.BookRideActivities.SearchActivity
import com.example.ecolift.Data_Classes.User
import com.example.ecolift.databinding.FragmentMainBinding

import com.example.ecolift.PostRideActivities.PostActivity
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(this.requireContext())
        val userName = sessionManager.fetchUserName()
        if(userName!=null && userName.isNotEmpty()){
            binding.userName.text = "Hi, $userName!"
            binding.progressBar.visibility = View.GONE
        }
        else
            getProfile()
        binding.BookRideBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        binding.PostRideBtn.setOnClickListener{
            val intent = Intent(requireContext(), PostActivity::class.java)
            startActivity(intent)
        }
    }

    fun getProfile(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
        binding.progressBar.visibility = View.VISIBLE


        retrofitBuilder.getProfile(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<User> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {

                    if(response.isSuccessful){
                        binding.progressBar.visibility = View.GONE
                        val responseBody = response.body()!!
                        var capitalName:String = responseBody.name
                        capitalName = capitalName.replaceFirstChar { it.uppercase() }
                        sessionManager.saveName(capitalName)
                        binding.userName.text = "Hi, $capitalName!"
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })

    }

}