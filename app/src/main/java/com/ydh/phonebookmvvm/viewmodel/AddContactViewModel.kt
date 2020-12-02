package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.request.BodyAddContact
import com.ydh.phonebookmvvm.view.state.ContactAddState
import com.ydh.phonebookmvvm.view.state.ContactListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class AddContactViewModelFactory(
    private val remoteRepository: ContactRemoteRepository,
    private val localRepository: ContactLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddContactViewModel(remoteRepository, localRepository) as T
    }
}

class AddContactViewModel (
    private val remoteRepository: ContactRemoteRepository,
    private val localRepository: ContactLocalRepository,
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<ContactAddState>() }
    val state: LiveData<ContactAddState> get() = mutableState
    private val token by lazy {
        UserSession(App.instance).token
    }

    fun insertContact(bodyAddContact: BodyAddContact) {
        mutableState.value = ContactAddState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contact = remoteRepository.addContact(token!!, bodyAddContact)
                mutableState.postValue(ContactAddState.SuccessInsertContact(contact.data))
            } catch (exc: Exception) {

                exc.printStackTrace()
                mutableState.postValue(ContactAddState.Error(exc))
            }
        }
    }

}