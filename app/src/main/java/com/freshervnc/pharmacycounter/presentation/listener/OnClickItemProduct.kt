package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Data


interface OnClickItemProduct {
    fun onClickItem(item : Data)
}