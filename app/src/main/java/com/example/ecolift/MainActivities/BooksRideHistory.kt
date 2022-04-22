package com.example.ecolift.MainActivities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecolift.Data_Classes.CreateTravllerResponse
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.example.ecolift.databinding.FragmentBooksRideHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksRideHistory : Fragment() {

    private var _binding: FragmentBooksRideHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBooksRideHistoryBinding.inflate(inflater,container,false)
        createTravller()
        return binding.root
    }


    fun createTravller() {

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
        binding.progressBarForList.visibility = View.VISIBLE

        retrofitBuilder.getHistory(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<List<CreateTravllerResponse>> {

                override fun onResponse(
                    call: Call<List<CreateTravllerResponse>>,
                    response: Response<List<CreateTravllerResponse>>
                ) {
                    if(response.isSuccessful){
                        binding.progressBarForList.visibility = View.GONE
                        val responseBody = response.body()!!
                        binding.allPostedrideRecylerView.layoutManager = LinearLayoutManager(requireContext())
                        val adapter = BookingHistoryRecylerView()
                        adapter.updateAll(responseBody)
                        binding.allPostedrideRecylerView.adapter = adapter
                        Log.d("succes", responseBody.toString())
                    }
                    else{
                        binding.progressBarForList.visibility = View.GONE
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<CreateTravllerResponse>>, t: Throwable) {
                    binding.progressBarForList.visibility = View.GONE
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })

    }

}