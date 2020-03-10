package com.gaohui.nestedrecyclerview.itemView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.gaohui.nestedrecyclerview.AdapterItemView
import com.gaohui.nestedrecyclerview.view.CategoryView
import com.gaohui.nestedrecyclerview.view.ChildRecyclerView
import com.gaohui.nestedrecyclerview.R
import com.gaohui.nestedrecyclerview.adapter.CategoryPagerAdapter
import com.gaohui.nestedrecyclerview.bean.CategoryBean
import com.google.android.material.tabs.TabLayout

class SimpleCategoryView constructor(context: Context) : LinearLayout(context),
    AdapterItemView<Any> {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    val viewList = ArrayList<ChildRecyclerView>()

    private var mCurrentRecyclerView: ChildRecyclerView? = null

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        return mCurrentRecyclerView
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_item_category, this)  //自己去负责inflate工作
        layoutParams= ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        orientation= VERTICAL
    }

    override fun getLayoutResId(): Int {
        return R.layout.layout_item_category
    }

    override fun initViews(var1: View) {
        mTabLayout = var1.findViewById(R.id.tabs) as TabLayout
        mViewPager = var1.findViewById(R.id.viewPager) as ViewPager
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (viewList.isEmpty().not()) {
                    mCurrentRecyclerView = viewList[position]
                    Log.d("gaohui", "onPageSelected: $position $mCurrentRecyclerView")
                    initCurrentRecyclerView()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun bindData(obj: Any, post: Int) {
        //TODO 需要优化，这里每次item被回收时都会重新setupWithViewPager
        (obj as? CategoryBean)?.apply {
            viewList.clear()
            tabTitleList.forEach { _ ->
                val categoryView = CategoryView(context)
                viewList.add(categoryView)
            }
            mCurrentRecyclerView = viewList[mViewPager.currentItem]
            val lastItem = mViewPager.currentItem
            Log.d("gaohui", "bindData: ${mViewPager.currentItem} $mCurrentRecyclerView")

            mViewPager.adapter = CategoryPagerAdapter(viewList, tabTitleList)
            mTabLayout.setupWithViewPager(mViewPager)
            mViewPager.currentItem = lastItem
            initCurrentRecyclerView()
        }
    }

    private fun initCurrentRecyclerView() {
        if (mCurrentRecyclerView is CategoryView)
            (mCurrentRecyclerView as CategoryView).initViews()
    }
}