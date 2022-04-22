package com.example.ecolift.Retrofit

import com.example.ecolift.Data_Classes.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface getInterface {

    @POST("/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/Logout")
    fun logout(@Header("Authorization")  token:String):Call<ResponseBody>

    @POST("/user")
    fun createUser(@Body request: CreateUser):Call<LoginResponse>

    @GET("/profile")
    fun getProfile(@Header("Authorization")  token:String):Call<User>

    @POST("/CreateRide")
    fun createRide(@Header("Authorization")  token:String, @Body request: CreateRide ):Call<ResponseBody>

    @GET("/allRide")
    fun getAllPostedRide(@Header("Authorization")  token:String):Call<List<AllPostedRide>>

    @POST("/searchRide")
    fun getAvailableRide(@Header("Authorization")  token:String, @Body request: Search_dataClass): Call<List<AllPostedRide>>


    @POST("/CreateTravller")
    fun createTravller(@Header("Authorization")  token:String, @Body request: Search_dataClass): Call<CreateTravllerResponse>

    @GET("/allTravller")
    fun getHistory(@Header("Authorization") token: String):Call<List<CreateTravllerResponse>>
}