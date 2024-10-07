package com.freshervnc.pharmacycounter.presentation.ui.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentManagerBinding
import com.freshervnc.pharmacycounter.presentation.ui.profile.ProfileFragment


class ManagerFragment : Fragment() {
    private lateinit var binding: FragmentManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentManagerBinding.inflate(layoutInflater ,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        features()
    }

    private fun features(){
        binding.managerLnInfo.setOnClickListener {
            (activity as MainActivity).replaceFragment((ProfileFragment()))
        }
        binding.managerLnStore.setOnClickListener {

        }
    }

}