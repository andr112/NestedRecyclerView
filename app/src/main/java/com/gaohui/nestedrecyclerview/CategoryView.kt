package com.gaohui.nestedrecyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import com.gaohui.nestedrecyclerview.adapter.MultiTypeAdapter

class CategoryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ChildRecyclerView(context, attrs, defStyleAttr) {

    private val mDataList = ArrayList<Any>()

    init {
        initRecyclerView()
        initLoadMore()

        initData()
    }

    private fun initRecyclerView() {
        val staggeredGridLayoutManager =
            androidx.recyclerview.widget.StaggeredGridLayoutManager(
                2,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )
        layoutManager = staggeredGridLayoutManager
        adapter = MultiTypeAdapter(mDataList)
    }

    private fun initLoadMore() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if ("no more now!" == mDataList.last()) {
                    return
                }
                val needLoadMore =
                    getLastVisibleItem(this@CategoryView) >= getTotalItemCount(this@CategoryView) - 4
                if (needLoadMore) {
                     if (getTotalItemCount(this@CategoryView) >= 15) {
                        mDataList.add("no more now!")
                        adapter?.notifyItemRangeChanged(mDataList.size - 1, mDataList.size)
                        return
                    }
                    onLoadMore()
                }
            }
        })

    }

    fun getLastVisibleItem(childRecyclerView: androidx.recyclerview.widget.RecyclerView): Int {
        val layoutManager = childRecyclerView.layoutManager
        return if (layoutManager != null && layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            val iArr = IntArray(2)
            layoutManager.findLastVisibleItemPositions(iArr)
            if (iArr[0] > iArr[1]) iArr[0] else iArr[1]
        } else  {
            -1
        }
    }

    fun getTotalItemCount(childRecyclerView: androidx.recyclerview.widget.RecyclerView): Int {
        return childRecyclerView.adapter?.itemCount?:-1
    }

    private fun initData() {
        for (i in 0..10) {
            mDataList.add("default child item $i")
        }
        adapter?.notifyDataSetChanged()
    }

    private fun onLoadMore() {
        val loadMoreSize = 6
        for (i in 0..loadMoreSize) {
            mDataList.add("load more child item $i")
        }
        adapter?.notifyItemRangeChanged(mDataList.size-loadMoreSize,mDataList.size)
    }

}