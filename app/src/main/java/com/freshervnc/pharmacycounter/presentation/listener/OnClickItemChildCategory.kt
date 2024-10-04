package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.response.category.Category


interface OnClickItemChildCategory {
    fun onClickItem(item : Category)
}