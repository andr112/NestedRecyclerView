package com.gaohui.nestedrecyclerview

import android.view.View

interface AdapterItemView<T> {
    fun getLayoutResId(): Int

    fun initViews(var1: View)

    fun bindData(data: T, post: Int)
}