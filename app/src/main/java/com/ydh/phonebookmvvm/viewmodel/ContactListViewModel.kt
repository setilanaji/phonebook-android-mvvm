package com.ydh.phonebookmvvm.viewmodel

import androidx.lifecycle.*
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.model.toEntity
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.local.entity.toModel
import com.ydh.phonebookmvvm.view.state.ContactListState
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
                onError(exc)
            }
        }
    }

    fun deleteContact(contactModel: ContactModel, position: Int) {
        mutableState.value = ContactListState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contactResponse = remoteRepository.deleteContact(token!!, contactModel.id)
                val model = contactResponse.data
                mutableState.postValue(ContactListState.SuccessDeleteContact(contactModel, position))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun getAllFav(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoEntityList = localRepository.getAllContact()

                if (!todoEntityList.isNullOrEmpty()){
                    val todoModelList = todoEntityList.asSequence().map { it.toModel() }.toList()
                    mutableState.postValue(ContactListState.SuccessGetAllFavorite(todoModelList))
                }else{
                    mutableState.postValue(ContactListState.EmptyFavorite("No Fav"))
                }
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun submitFavorite(contactModel: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (localRepository.isFavorite(contactModel.id)) {
                    deleteFavorite(contactModel)
                } else {
                    insertFavorite(contactModel)
                }
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun insertFavorite(contactModel: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localRepository.insertContact(contactModel.toEntity())
                mutableState.postValue(ContactListState.SuccessInsertFavorite(contactModel))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }


    private fun deleteFavorite(contactModel: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localRepository.deleteContact(contactModel.toEntity())
                mutableState.postValue(ContactListState.SuccessDeleteFavorite(contactModel))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun onError(exc: Exception) {
        exc.printStackTrace()
        mutableState.postValue(ContactListState.Error(exc))
    }

}