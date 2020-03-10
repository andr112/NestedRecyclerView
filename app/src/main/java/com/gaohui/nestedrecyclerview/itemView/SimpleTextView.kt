package com.gaohui.nestedrecyclerview.itemView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.gaohui.nestedrecyclerview.AdapterItemView
import com.gaohui.nestedrecyclerview.R
import com.gaohui.nestedrecyclerview.view.utils.UIUtils
import kotlinx.android.synthetic.main.layout_item_text.view.*

class SimpleTextView constructor(context: Context) : RelativeLayout(context),
    AdapterItemView<Any> {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_item_text, this)  //自己去负责inflate工作

    }

    override fun getLayoutResId(): Int {
        return R.layout.layout_item_text
    }

    override fun initViews(var1: View) {
        layoutParams= LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dp2px(120f))
    }

    override fun bindData(data: Any, post: Int) {
        textView.text = if (data is String) data else data.toString()
    }
}