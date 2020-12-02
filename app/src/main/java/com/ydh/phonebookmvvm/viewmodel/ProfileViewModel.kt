package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.view.state.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val remoteRepository: UserRemoteRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(remoteRepository) as T
    }
}


class ProfileViewModel(
    private val remoteRepository: UserRemoteRepository
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<SignInState>() }
    val state: LiveData<SignInState> get() = mutableState
    private val prefs by lazy {
        UserSession(App.instance)
    }

    fun login(email: String, password: String) {
        val body = LoginBody(email, password)
        mutableState.value = SignInState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userModel = remoteRepository.userLogin(body)
                prefs.token = "Bearer ${userModel.data.token}"
                prefs.name = userModel.data.name
                prefs.email = userModel.data.email
                prefs.loggedIn = true
                mutableState.postValue(SignInState.SuccessSignIn(userModel.data))
            } catch (exc: Exception) {

                exc.printStackTrace()
                mutableState.postValue(SignInState.Error(exc))
            }
        }
    }

}