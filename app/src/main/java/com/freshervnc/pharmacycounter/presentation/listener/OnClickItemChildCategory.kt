package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Category


interface OnClickItemChildCategory {
    fun onClickItem(item : Category)
}