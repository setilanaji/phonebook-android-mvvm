package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.view.state.ContactDetailState
import com.ydh.phonebookmvvm.view.state.ContactListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class ContactDetailViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactDetailViewModel() as T
    }
}
class ContactDetailViewModel () : ViewModel() {
    private val mutableState by lazy { MutableLiveData<ContactDetailState>() }
    val state: LiveData<ContactDetailState> get() = mutableState


    fun getContact() {
        mutableState.value = ContactDetailState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                mutableState.postValue(ContactDetailState.SuccessGetAllContact(contactList.data))
            } catch (exc: Exception) {

                exc.printStackTrace()
                mutableState.postValue(ContactDetailState.Error(exc))
            }
        }
    }

}