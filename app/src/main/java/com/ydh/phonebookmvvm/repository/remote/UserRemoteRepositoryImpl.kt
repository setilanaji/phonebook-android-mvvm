package com.ydh.phonebookmvvm.repository.remote

import com.ydh.phonebookmvvm.repository.remote.request.BodyRegistration
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import com.ydh.phonebookmvvm.model.UserModel
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.service.UserService
import retrofit2.Call

class UserRemoteRepositoryImpl (private val service: UserService): UserRemoteRepository{

    override suspend fun userLogin(loginBody: LoginBody): ResponseModel<UserModel>{
        return service.userLogin(loginBody)
    }

    override suspend fun userRegister(bodyRegistration: BodyRegistration): ResponseModel<String>{
        return service.userRegister(bodyRegistration)
    }

}