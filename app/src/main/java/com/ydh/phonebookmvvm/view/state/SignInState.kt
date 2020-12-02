package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.UserModel
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody

sealed class SignInState {

    data class Loading(val message: String = "Loading.."): SignInState()
    data class Error(val exception: Exception) : SignInState()
    data class SuccessSignIn(val data: UserModel) : SignInState()
    data class OnFailed(val message: String): SignInState()

}
