package com.freshervnc.pharmacycounter.presentation.listener

import com.freshervnc.pharmacycounter.domain.models.Data


interface OnClickItemStoreProduct {
    fun onClickDelete(item : Data)
    fun onClickUpdate(item : Data)
    fun onClickHaveInStore()
}