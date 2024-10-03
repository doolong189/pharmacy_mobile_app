package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.response.homepage.Data

interface OnClickItemProduct {
    fun onClickItem(item : Data)
}