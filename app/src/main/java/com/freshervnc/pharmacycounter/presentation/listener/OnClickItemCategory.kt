package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.response.category.Category


interface OnClickItemCategory {
    fun onClickItem(item : Category)
}