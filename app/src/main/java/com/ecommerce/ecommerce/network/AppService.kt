package com.ecommerce.ecommerce.network

import com.ecommerce.ecommerce.models.AuthResponse
import com.ecommerce.ecommerce.models.LoginRequest
import com.ecommerce.ecommerce.models.RegisterResponse
import com.ecommerce.ecommerce.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>


    @POST("api/auth/register")
    suspend fun register(@Body user: User): Response<RegisterResponse>
}
