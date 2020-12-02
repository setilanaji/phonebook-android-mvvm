package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentSignInBinding
import com.ydh.phonebookmvvm.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


}