package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.model.UserModel
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.request.BodyRegistration
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.view.state.SignInState
import com.ydh.phonebookmvvm.view.state.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(
        private val remoteRepository: UserRemoteRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(remoteRepository) as T
    }
}

class SignUpViewModel(
        private val remoteRepository: UserRemoteRepository
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<SignUpState>() }
    val state: LiveData<SignUpState> get() = mutableState
    private val prefs by lazy {
        UserSession(App.instance)
    }

    fun register(name: String, email: String, password: String) {
        val body = BodyRegistration(name,email, password)
        mutableState.value = SignUpState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = remoteRepository.userRegister(body)
                prefs.token = "Bearer ${token.data}"
                prefs.name = body.name
                prefs.email = body.email
                prefs.loggedIn = true
                mutableState.postValue(SignUpState.SuccessSignUp(UserModel(body.name, body.email,token.data)))
            } catch (exc: Exception) {

                exc.printStackTrace()
                mutableState.postValue(SignUpState.Error(exc))
            }
        }
    }

}