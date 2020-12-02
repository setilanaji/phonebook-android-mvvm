package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentAddBinding
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.local.ContactLocalRepositoryImpl
import com.ydh.phonebookmvvm.repository.local.dao.ContactDao
import com.ydh.phonebookmvvm.repository.local.database.LocalDB
import com.ydh.phonebookmvvm.repository.remote.ContactRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.client.Api
import com.ydh.phonebookmvvm.repository.remote.request.BodyAddContact
import com.ydh.phonebookmvvm.repository.remote.service.ContactService
import com.ydh.phonebookmvvm.view.state.ContactAddState
import com.ydh.phonebookmvvm.view.state.ContactListState
import com.ydh.phonebookmvvm.viewmodel.AddContactViewModel
import com.ydh.phonebookmvvm.viewmodel.AddContactViewModelFactory
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModel
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModelFactory


class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    private val service: ContactService by lazy { Api.contactService }
    private val dao: ContactDao by lazy { LocalDB.getDB(requireContext()).dao() }
    private val remoteRepository: ContactRemoteRepository by lazy { ContactRemoteRepositoryImpl(service) }
    private val localRepository: ContactLocalRepository by lazy { ContactLocalRepositoryImpl(dao) }
    private val viewModelFactory by lazy { AddContactViewModelFactory(remoteRepository, localRepository) }
    private val viewModel by viewModels<AddContactViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        setView()
        setObserver()
        return binding.root
    }

    private fun setView(){
        binding.run {
            btAddContact.setOnClickListener {
                viewModel.insertContact(
                    BodyAddContact(
                        etAddContactName.text.toString(),
                        etAddContactPhone.text.toString(),
                        etAddContactJob.text.toString(),
                        etAddContactCompany.text.toString(),
                        etAddContactEmail.text.toString()
                    )
                )
            }
        }
    }
    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is ContactAddState.Loading -> showLoading(true)
                is ContactAddState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message ?: "Oops something went wrong")
                }
                is ContactAddState.SuccessInsertContact -> {
                    showLoading(false)
                    requireActivity().onBackPressed()
                }
                else -> throw Exception("Unsupported state type")

            }
        }
    }

    private fun showLoading(state: Boolean){
        binding.apply {
            btAddContact.text = if (state) "" else "add"
            pbAdd.visibility = if (state) View.VISIBLE else View.GONE
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }



}