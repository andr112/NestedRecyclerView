package com.gaohui.nestedrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CommonRvAdapter<T>(private val dataSource: List<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = createItem(viewType)
        return CommonViewHolder(parent.context, parent, item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val commonViewHolder = holder as CommonViewHolder<T>
        commonViewHolder.adapterItemView.bindData(dataSource[position], position)
    }

    override fun getItemCount() = dataSource.size

    override fun getItemViewType(position: Int): Int {
        return getItemType(dataSource[position])
    }

    /**
     * @param viewType 需要创建的ItemView的viewType, 由 {@link getItemType(item: T)} 根据数据产生
     * @return 返回这个 viewType 对应的 AdapterItemView
     * */
    abstract fun createItem(viewType: Int): AdapterItemView<T>

    /**
     * @param T 代表dataSource中的一个data
     *
     * @return 返回 显示 T 类型的data的 ItemView的 类型
     * */
    abstract fun getItemType(item: T): Int

    /**
     * Wrapper 的ViewHolder。 业务不必理会RecyclerView的ViewHolder
     * */
    private class CommonViewHolder<T>(
        context: Context?,
        parent: ViewGroup,
        val adapterItemView: AdapterItemView<T>
    )
    //这一点做了特殊处理，如果业务的AdapterItemView本身就是一个View，那么直接当做ViewHolder的itemView。 否则inflate出一个view来当做ViewHolder的itemView
        : RecyclerView.ViewHolder(
        if (adapterItemView is View) adapterItemView else LayoutInflater.from(context).inflate(
            adapterItemView.getLayoutResId(),
            parent,
            false
        )
    ) {
        init {
            adapterItemView.initViews(itemView)
        }
    }
}