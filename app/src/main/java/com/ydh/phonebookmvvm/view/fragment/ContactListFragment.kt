package com.ydh.phonebookmvvm.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.ContactMenuDialogBinding
import com.ydh.phonebookmvvm.databinding.FragmentContactListBinding
import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.local.ContactLocalRepositoryImpl
import com.ydh.phonebookmvvm.repository.local.dao.ContactDao
import com.ydh.phonebookmvvm.repository.local.database.LocalDB
import com.ydh.phonebookmvvm.repository.remote.ContactRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.client.Api
import com.ydh.phonebookmvvm.repository.remote.service.ContactService
import com.ydh.phonebookmvvm.view.adapter.ContactAdapter
import com.ydh.phonebookmvvm.view.adapter.FavoriteAdapter
import com.ydh.phonebookmvvm.view.state.ContactListState
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModel
import com.ydh.phonebookmvvm.viewmodel.ContactListViewModelFactory
import kotlinx.android.synthetic.main.fragment_contact_list.*


class ContactListFragment : Fragment(), ContactAdapter.ContactListener,
    FavoriteAdapter.FavoriteListener {

    lateinit var binding: FragmentContactListBinding
    private val adapter by lazy { ContactAdapter(requireActivity(), this) }
    private val favAdapter by lazy { FavoriteAdapter(requireActivity(), this) }
    private val service: ContactService by lazy { Api.contactService }
    private val dao: ContactDao by lazy { LocalDB.getDB(requireContext()).dao() }
    private val remoteRepository: ContactRemoteRepository by lazy {
        ContactRemoteRepositoryImpl(
            service
        )
    }
    private val localRepository: ContactLocalRepository by lazy { ContactLocalRepositoryImpl(dao) }
    private val viewModelFactory by lazy {
        ContactListViewModelFactory(
            remoteRepository,
            localRepository
        )
    }
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


    private fun setView() {

        val colorDrawable = ColorDrawable(resources.getColor(R.color.bg_main))
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(colorDrawable)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0.0F
        (activity as AppCompatActivity).supportActionBar?.title = "Get Contact"

        setHasOptionsMenu(true)

        binding.run {
            rvContactList.adapter = adapter
            rvFavorite.adapter = favAdapter
            swipeRefresh.setOnRefreshListener {
                onResume() // refresh your list contents somehow
                swipeRefresh.isRefreshing = false
            }

        }
    }

    private fun setObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ContactListState.Loading -> showLoading(true)
                is ContactListState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message ?: "Oops something went wrong")
                }
                is ContactListState.SuccessGetAllContact -> {
                    adapter.generateContact(it.list.toMutableList())
                    showLoading(false)
                }
                is ContactListState.SuccessDeleteContact -> {
                    adapter.deleteContact(it.position)
                    showMessage("${it.list.name} has been deleted from contact")
                    onResume()
                }
                is ContactListState.SuccessGetAllFavorite -> {
                    favAdapter.list = it.list.toMutableList()
                    hideFavBar(false)
                }
                is ContactListState.SuccessInsertFavorite -> {
                    favAdapter.insertTodo(it.list)
                    hideFavBar(false)

                }
                is ContactListState.SuccessDeleteFavorite -> {
                    favAdapter.deleteTodo(it.list)
                    viewModel.getAllFav()

                }
                is ContactListState.EmptyFavorite -> {
                    hideFavBar(true)
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
        return when (item.itemId) {
            R.id.addFragment -> {
                val action = ContactListFragmentDirections.actionContactListFragmentToAddFragment(
                    null,
                    "ADD"
                )
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllContact()
        viewModel.getAllFav()
    }

    private fun hideFavBar(isEmpty: Boolean) {
        binding.run {
            rvFavorite.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
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
        val action = ContactListFragmentDirections.actionContactListFragmentToContactDetailFragment(
            contactModel
        )
        findNavController().navigate(action)
    }

    override fun onDelete(contactModel: ContactModel, position: Int) {
        viewModel.deleteContact(contactModel, position)
    }

    override fun onFavorite(contactModel: ContactModel) {
        viewModel.submitFavorite(contactModel)
    }

    override fun onLongPress(contactModel: ContactModel, position: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val x = ContactMenuDialogBinding.inflate(LayoutInflater.from(context), null, false)
        x.run {
            tvName.text = contactModel.name
            tvView.setOnClickListener {
                dialog.hide()
                onClick(contactModel)
            }
            tvEdit.setOnClickListener {
                dialog.hide()
                val action = ContactListFragmentDirections.actionContactListFragmentToAddFragment(
                    contactModel,
                    "EDIT"
                )
                findNavController().navigate(action)
            }
            tvFavorite.setOnClickListener {
                dialog.hide()
                onFavorite(contactModel)
            }
            tvDelete.setOnClickListener {
                dialog.hide()
                onDelete(contactModel, position)
            }

        }
        dialog.setContentView(x.root)
        dialog.show()
    }


}