package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ydh.phonebookmvvm.App
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.UserSession
import com.ydh.phonebookmvvm.databinding.FragmentSignInBinding
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.UserRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.client.Api
import com.ydh.phonebookmvvm.repository.remote.service.UserService
import com.ydh.phonebookmvvm.view.state.SignInState
import com.ydh.phonebookmvvm.viewmodel.SignInViewModel
import com.ydh.phonebookmvvm.viewmodel.SignInViewModelFactory
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val service: UserService by lazy { Api.userService }
    private val remoteRepository: UserRemoteRepository by lazy { UserRemoteRepositoryImpl(service) }
    private val viewModelFactory by lazy { SignInViewModelFactory(remoteRepository) }
    private val viewModel by viewModels<SignInViewModel> { viewModelFactory }

    private val prefs: UserSession by lazy {
        UserSession(App.instance)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        if (prefs.loggedIn) {
            findNavController().navigate(R.id.homeFragment)
        }

        setView()
        setObserver()

        return binding.root
    }

    private fun setView(){

        binding.btLogLogin.setOnClickListener{
            viewModel.login(
                et_login_user_email.text.toString(),
                et_login_user_password.text.toString())
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is SignInState.Loading -> showLoading(true)
                is SignInState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message ?: "Oops something went wrong")
                }
                is SignInState.SuccessSignIn -> {
                    showLoading(false)
                    findNavController().navigate(R.id.homeFragment)
                    }
                is SignInState.OnFailed -> showMessage(it.message)
                else -> throw Exception("Unsupported state type")

            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
            pbSignIn.visibility = if (isLoading) View.VISIBLE else View.GONE
            cvSignIn.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

}