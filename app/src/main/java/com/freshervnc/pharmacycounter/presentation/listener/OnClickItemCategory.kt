package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Category


interface OnClickItemCategory {
    fun onClickItem(item : Category)
}