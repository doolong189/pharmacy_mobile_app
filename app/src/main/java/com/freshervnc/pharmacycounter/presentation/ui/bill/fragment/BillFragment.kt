package com.freshervnc.pharmacycounter.presentation.ui.bill.fragment

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentBillBinding
import com.freshervnc.pharmacycounter.presentation.ui.bill.adapter.BillViewPagerAdapter


class BillFragment : Fragment() {
    private lateinit var binding : FragmentBillBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBillBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
        customTabLayout()
    }


    private fun action() {
        (activity as MainActivity).showBottomNav()
        val adapter: BillViewPagerAdapter = BillViewPagerAdapter(requireActivity().supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.billViewPager.setAdapter(adapter)
        binding.billTabLayout.setupWithViewPager(binding.billViewPager)
    }

    private fun customTabLayout() {
        val tabCount: Int = binding.billTabLayout.tabCount
        for (i in 0 until tabCount) {
            val tabView = (binding.billTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            tabView.requestLayout()
            ViewCompat.setBackground(tabView, setImageButtonStateNew(requireContext()))
            ViewCompat.setPaddingRelative(tabView, tabView.paddingStart, tabView.paddingTop, tabView.paddingEnd, tabView.paddingBottom)
        }
    }

    private fun setImageButtonStateNew(mContext: Context?): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_selected), ContextCompat.getDrawable(mContext!!, R.drawable.tab_bg_normal_blue))
        states.addState(intArrayOf(-android.R.attr.state_selected), ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal))
        return states
    }
}