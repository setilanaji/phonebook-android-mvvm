package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.UserModel

sealed class SignUpState {

    data class Loading(val message: String = "Loading.."): SignUpState()
    data class Error(val exception: Exception) : SignUpState()
    data class SuccessSignUp(val data: UserModel) : SignUpState()
    data class OnFailed(val message: String): SignUpState()

}