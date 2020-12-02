package com.ydh.phonebookmvvm.view.state

import com.ydh.phonebookmvvm.model.ContactModel

sealed class ContactAddState {
    data class Loading(val message: String = "Loading...") : ContactAddState()
    data class Error(val exception: Exception) : ContactAddState()
    data class SuccessInsertContact(val todo: ContactModel) : ContactAddState()
}