package com.ydh.phonebookmvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {
    lateinit var binding: FragmentContactDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_detail, container, false)

        setView()


        return binding.root
    }

     private fun setView(){

         binding.run {
            arguments?.let {
                val args = ContactDetailFragmentArgs.fromBundle(it)
                println(args.contact.toString())
                binding.contact = args.contact
            }
             btDetailEdit.setOnClickListener {
                 val action = ContactDetailFragmentDirections.actionContactDetailFragmentToAddFragment(binding.contact, "EDIT")
                 findNavController().navigate(action)

             }
        }
    }

    companion object{
        @JvmStatic
        @BindingAdapter("contactImage")
        fun loadImage(view: ImageView, url: String?){
            var imageUrl = url
            if (imageUrl.isNullOrEmpty()){
                imageUrl = "https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png"
            }
            Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
        }
    }


}