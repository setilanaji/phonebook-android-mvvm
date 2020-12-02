package com.ydh.phonebookmvvm.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentContactListBinding
import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.local.ContactLocalRepositoryImpl
import com.ydh.phonebookmvvm.repository.local.dao.ContactDao
import com.ydh.phonebookmvvm.repository.local.database.LocalDB
import com.ydh.phonebookmvvm.repository.remote.ContactRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.UserRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.client.Api
import com.ydh.phonebookmvvm.repository.remote.service.ContactService
import com.ydh.phonebookmvvm.repository.remote.service.UserService
import com.ydh.phonebookmvvm.view.adapter.ContactAdapter
import com.ydh.phonebookmvvm.view.state.ContactListState
import com.ydh.phonebookmvvm.view.state.SignInState
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModel
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModelFactory
import com.ydh.phonebookmvvm.viewmodel.SignInViewModel
import com.ydh.phonebookmvvm.viewmodel.SignInViewModelFactory


class ContactListFragment : Fragment(), ContactAdapter.ContactListerner {

    lateinit var binding: FragmentContactListBinding
    private val adapter by lazy { ContactAdapter(requireActivity(), this) }
    private val service: ContactService by lazy { Api.contactService }
    private val dao: ContactDao by lazy { LocalDB.getDB(requireContext()).dao() }
    private val remoteRepository: ContactRemoteRepository by lazy { ContactRemoteRepositoryImpl(service) }
    private val localRepository: ContactLocalRepository by lazy { ContactLocalRepositoryImpl(dao) }
    private val viewModelFactory by lazy { ContactListViewModelFactory(remoteRepository, localRepository) }
    private val viewModel by viewModels<ContactListViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)

        setView()
        setObserver()


        return binding.root
    }

    private fun setView(){
        val colorDrawable = ColorDrawable(resources.getColor(R.color.bg_main))
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(colorDrawable)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0.0F
        (activity as AppCompatActivity).supportActionBar?.title = "Get Contact"


        setHasOptionsMenu(true)


        binding.apply {
            rvContactList.adapter = adapter
        }

    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is ContactListState.Loading -> showLoading(true)
                is ContactListState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message ?: "Oops something went wrong")
                }
                is ContactListState.SuccessGetAllContact -> {
                    adapter.generateContact(it.list.toMutableList())
                    showLoading(false)
                }
                else -> throw Exception("Unsupported state type")

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
                item,
                requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllContact()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
            pbRv.visibility = if (isLoading) View.VISIBLE else View.GONE
            rvContactList.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onClick(contactModel: ContactModel) {
        val action = ContactListFragmentDirections.actionContactListFragmentToContactDetailFragment(contactModel)
        findNavController().navigate(action)
    }

    override fun onDelete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onChange(contactModel: ContactModel) {
        TODO("Not yet implemented")
    }


}