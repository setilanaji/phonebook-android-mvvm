package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.request.LoginBody
import com.ydh.phonebookmvvm.view.state.ContactListState
import com.ydh.phonebookmvvm.view.state.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class ContactListViewModelFactory(
    private val remoteRepository: ContactRemoteRepository,
    private val localRepository: ContactLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactListViewModel(remoteRepository, localRepository) as T
    }
}

class ContactListViewModel (
    private val remoteRepository: ContactRemoteRepository,
    private val localRepository: ContactLocalRepository,
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<ContactListState>() }
    val state: LiveData<ContactListState> get() = mutableState
    private val token by lazy {
        UserSession(App.instance).token
    }

    fun getAllContact() {
        mutableState.value = ContactListState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contactList = remoteRepository.getAllContact(token!!)
                mutableState.postValue(ContactListState.SuccessGetAllContact(contactList.data))
            } catch (exc: Exception) {

                exc.printStackTrace()
                mutableState.postValue(ContactListState.Error(exc))
            }
        }
    }

}