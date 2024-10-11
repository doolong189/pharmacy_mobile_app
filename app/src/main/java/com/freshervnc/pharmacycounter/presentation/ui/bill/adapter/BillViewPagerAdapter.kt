package com.freshervnc.pharmacycounter.presentation.ui.bill.adapter

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.BillFragment
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.PurchaseHistoryFragment
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.SalesHistoryFragment

class BillViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    @NonNull
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PurchaseHistoryFragment()
            1 -> SalesHistoryFragment()
            else -> BillFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Lịch sử mua"
            1 -> "Lịch sử bán"
            else -> null
        }
    }
}
