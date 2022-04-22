package com.example.ecolift.MainActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.example.ecolift.StartActivities.LoginActivity
import com.example.ecolift.databinding.FragmentProfileBinding
import com.example.ecolift.databinding.FragmentProfileSettingBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSettingFragment : Fragment() {

    private var _binding: FragmentProfileSettingBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager:SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileSettingBinding.inflate(inflater,container,false)

        sessionManager = SessionManager(this.requireContext())
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

        return binding.root
    }


    fun logout(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
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
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })
    }


}