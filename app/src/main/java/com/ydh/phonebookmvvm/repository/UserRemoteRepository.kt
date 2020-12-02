package com.ydh.phonebookmvvm.repository

import com.ydh.phonebookmvvm.repository.remote.request.BodyRegistration
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import com.ydh.phonebookmvvm.model.UserModel
import retrofit2.Call

interface UserRemoteRepository {

    suspend fun userLogin(loginBody: LoginBody): ResponseModel<UserModel>
    suspend fun userRegister(bodyRegistration: BodyRegistration): ResponseModel<String>
    suspend fun logout()
}