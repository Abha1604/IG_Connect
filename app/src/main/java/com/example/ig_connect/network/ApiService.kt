package com.example.ig_connect.api

import com.example.ig_connect.data.models.LoginRequest
import com.example.ig_connect.data.models.LoginResponse
import com.example.ig_connect.data.models.ProfileDetailsRequest
import com.example.ig_connect.data.models.ProfileDetailsResponse
import com.example.ig_connect.data.models.RegisterRequest
import com.example.ig_connect.data.models.RegisterResponse
import com.example.ig_connect.data.models.UserProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("api/user/register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>


    @POST("api/user/userDetails")  // Ensure this endpoint matches your API
    fun updateProfileDetails(
        @Header("Authorization") token: String, // Dynamic header for Authorization
        @Body profileDetails: ProfileDetailsRequest // The request body
    ): Call<ProfileDetailsResponse>

    @POST("api/user/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("user/profile")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<UserProfileResponse>


}
