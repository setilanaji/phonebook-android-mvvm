package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.ContactModel

sealed class ContactDetailState {
    data class Loading(val message: String = "Loading...") : ContactDetailState()
    data class Error(val exception: Exception) : ContactDetailState()
    data class SuccessGetAllContact(val list: List<ContactModel>) : ContactDetailState()
    data class SuccessInsertContact(val todo: ContactModel) : ContactDetailState()
    data class SuccessUpdateContact(val todo: ContactModel) : ContactDetailState()
    data class SuccessDeleteContact(val todo: ContactModel) : ContactDetailState()
    data class SuccessGetAllFavorite(val list: List<ContactModel>) : ContactDetailState()
    data class SuccessInsertFavorite(val todo: ContactModel) : ContactDetailState()
    data class SuccessDeleteFavorite(val todo: ContactModel) : ContactDetailState()
}