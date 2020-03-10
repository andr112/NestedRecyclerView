package com.gaohui.nestedrecyclerview.adapter

import android.content.Context
import com.gaohui.nestedrecyclerview.AdapterItemView
import com.gaohui.nestedrecyclerview.view.ChildRecyclerView
import com.gaohui.nestedrecyclerview.CommonRvAdapter
import com.gaohui.nestedrecyclerview.bean.CategoryBean
import com.gaohui.nestedrecyclerview.itemView.SimpleCategoryView
import com.gaohui.nestedrecyclerview.itemView.SimpleTextView

class MultiTypeAdapter(private val context: Context, private val dataSet: ArrayList<Any>) :
    CommonRvAdapter<Any>(dataSet) {

    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_CATEGORY = 1
    }

    private var mCategoryView: SimpleCategoryView? = null

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        mCategoryView?.apply {
            return this.getCurrentChildRecyclerView()
        }
        return null
    }

    override fun createItem(viewType: Int): AdapterItemView<Any> {
        when (viewType) {
            TYPE_CATEGORY -> {
                mCategoryView = SimpleCategoryView(context)
                return mCategoryView!!
            }
            else -> return SimpleTextView(context)
        }
    }

    override fun getItemType(item: Any): Int {
        return if (item is CategoryBean) {
            TYPE_CATEGORY
        } else {
            TYPE_TEXT
        }
    }
}