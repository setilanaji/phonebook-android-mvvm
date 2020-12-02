package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.ContactModel


sealed class ContactListState {
    data class Loading(val message: String = "Loading...") : ContactListState()
    data class Error(val exception: Exception) : ContactListState()
    data class SuccessGetAllContact(val list: List<ContactModel>) : ContactListState()
    data class SuccessDeleteContact(val list: ContactModel) : ContactListState()
    data class SuccessGetAllFavorite(val list: List<ContactModel>) : ContactListState()
    data class SuccessInsertFavorite(val list: ContactModel) : ContactListState()
    data class SuccessDeleteFavorite(val list: ContactModel) : ContactListState()
    data class EmptyFavorite(val message: String): ContactListState()
}