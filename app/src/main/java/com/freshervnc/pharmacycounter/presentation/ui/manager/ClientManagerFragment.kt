package com.freshervnc.pharmacycounter.presentation.ui.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentClientManagerBinding


class ClientManagerFragment : Fragment() {
    private lateinit var binding : FragmentClientManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentClientManagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}