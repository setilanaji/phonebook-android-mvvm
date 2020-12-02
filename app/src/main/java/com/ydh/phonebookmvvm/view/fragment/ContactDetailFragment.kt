package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {
    lateinit var binding: FragmentContactDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        return inflater.inflate(R.layout.fragment_contact_detail, container, false)
    }

}