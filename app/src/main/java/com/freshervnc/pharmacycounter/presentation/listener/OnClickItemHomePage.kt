package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Product

interface OnClickItemHomePage {
    fun onClickItem(item : Product)
}