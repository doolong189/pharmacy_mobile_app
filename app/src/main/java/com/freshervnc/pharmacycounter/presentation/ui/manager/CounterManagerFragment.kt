package com.freshervnc.pharmacycounter.presentation.ui.manager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.databinding.FragmentManagerBinding
import com.freshervnc.pharmacycounter.presentation.ui.manager.news.NewsFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.profile.CounterProfileFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.store.StoreFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.viewmodel.ManagerViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.RegistrationActivity
import com.freshervnc.pharmacycounter.presentation.ui.webview.WebViewFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class CounterManagerFragment : Fragment() {
    private lateinit var binding: FragmentManagerBinding
    private lateinit var managerViewModel: ManagerViewModel
    private lateinit var mySharedPrefer: SharedPrefer
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
        init()
        features()
    }

    private fun init(){
        (activity as MainActivity).showBottomNav()
        managerViewModel = ViewModelProvider(
            this,
            ManagerViewModel.ManagerViewModelFactory(requireActivity().application)
        )[ManagerViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }
    private fun features(){
        binding.managerLnInfo.setOnClickListener {
            (activity as MainActivity).replaceFragment((CounterProfileFragment()))
        }
        binding.managerLnStore.setOnClickListener {
            (activity as MainActivity).replaceFragment((StoreFragment()))
        }
        binding.managerLnContact.setOnClickListener {
            (activity as MainActivity).replaceFragment(com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.ContactFragment())
        }
        binding.managerLnNews.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewsFragment())
        }
        binding.managerLnIntro.setOnClickListener {
            replaceFragment(1)
        }

        binding.managerLnClause.setOnClickListener {
            replaceFragment(2)
        }
        binding.managerLnSecurity.setOnClickListener {
            replaceFragment(3)
        }
        binding.managerLnBuyingGuide.setOnClickListener {
            replaceFragment(4)
        }
        binding.managerLnPolicyPayment.setOnClickListener {
            replaceFragment(5)
        }
        binding.managerLnPolicyShipping.setOnClickListener {
            replaceFragment(6)
        }
        binding.managerLnPolicyReturn.setOnClickListener {
            replaceFragment(7)
        }
        binding.managerLnPolicyWarranty.setOnClickListener {
            replaceFragment(8)
        }
        binding.managerLnPolicyCheckGood.setOnClickListener {
            replaceFragment(9)
        }
        binding.managerLnLogout.setOnClickListener {
            managerViewModel.requestLogOut("Bearer "+mySharedPrefer.token).observe(viewLifecycleOwner,
                Observer {
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                startActivity(Intent(requireActivity(),RegistrationActivity::class.java))
                                mySharedPrefer.clearUserData()
                                requireActivity().finish()
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
        }
    }

    fun replaceFragment(idPage : Int){
        val args = Bundle()
        args.putInt("idPage", idPage)
        val newFragment: WebViewFragment = WebViewFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }
}