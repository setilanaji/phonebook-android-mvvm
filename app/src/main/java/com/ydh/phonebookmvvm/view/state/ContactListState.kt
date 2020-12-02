package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.ContactModel


sealed class ContactListState {
    data class Loading(val message: String = "Loading...") : ContactListState()
    data class Error(val exception: Exception) : ContactListState()
    data class SuccessGetAllContact(val list: List<ContactModel>) : ContactListState()
    data class SuccessInsertContact(val todo: ContactModel) : ContactListState()
    data class SuccessUpdateContact(val todo: ContactModel) : ContactListState()
    data class SuccessDeleteContact(val todo: ContactModel) : ContactListState()
    data class SuccessGetAllFavorite(val list: List<ContactModel>) : ContactListState()
    data class SuccessInsertFavorite(val todo: ContactModel) : ContactListState()
    data class SuccessDeleteFavorite(val todo: ContactModel) : ContactListState()
}