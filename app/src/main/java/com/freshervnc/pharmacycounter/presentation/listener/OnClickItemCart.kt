package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Data


interface OnClickItemCart {
    fun onClickItem(item : Data, amount : Int, status: Boolean)

    fun onClickItemDelete(item : Data, number: Int)
}