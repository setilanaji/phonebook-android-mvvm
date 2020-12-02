package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentSignUpBinding
import com.ydh.phonebookmvvm.repository.UserRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.UserRemoteRepositoryImpl
import com.ydh.phonebookmvvm.repository.remote.client.Api
import com.ydh.phonebookmvvm.repository.remote.service.UserService
import com.ydh.phonebookmvvm.view.state.SignUpState
import com.ydh.phonebookmvvm.viewmodel.SignUpViewModel
import com.ydh.phonebookmvvm.viewmodel.SignUpViewModelFactory
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val service: UserService by lazy { Api.userService }
    private val remoteRepository: UserRemoteRepository by lazy { UserRemoteRepositoryImpl(service) }
    private val viewModelFactory by lazy { SignUpViewModelFactory(remoteRepository) }
    private val viewModel by viewModels<SignUpViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        setView()
        setObserver()

        return binding.root
    }


    private fun setView(){

        binding.btRegRegister.setOnClickListener{
            viewModel.register(
                    et_register_user_name.text.toString(),
                    et_register_user_email.text.toString(),
                    et_register_user_password.text.toString())
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is SignUpState.Loading -> showLoading(true)
                is SignUpState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message ?: "Oops something went wrong")
                }
                is SignUpState.SuccessSignUp -> {
                    showLoading(false)
                    findNavController().navigate(R.id.homeFragment)
                }
                is SignUpState.OnFailed -> showMessage(it.message)
                else -> throw Exception("Unsupported state type")

            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
            pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
            cvRegister.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }


}