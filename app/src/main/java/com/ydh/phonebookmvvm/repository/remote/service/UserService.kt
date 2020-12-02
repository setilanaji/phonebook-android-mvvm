package com.ydh.phonebookmvvm.repository.remote.service

import com.ydh.phonebookmvvm.repository.remote.request.BodyRegistration
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import com.ydh.phonebookmvvm.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/v1/signin")
    suspend fun userLogin(
        @Body
        loginBody: LoginBody
    ): ResponseModel<UserModel>

    @POST("api/v1/signup")
    suspend fun userRegister(
        @Body
        registration: BodyRegistration
    ): ResponseModel<String>

}