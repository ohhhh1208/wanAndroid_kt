package com.ouo.core.view.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by oxq on 2023/12/21.
 */

abstract class BaseRecyclerAdapter<V : ViewBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder<V>>() {

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerHolder<V>

    override fun onBindViewHolder(holder: BaseRecyclerHolder<V>, position: Int) {
        onBaseBindViewHolder(position, holder.binding)
    }

    abstract fun onBaseBindViewHolder(position: Int, binding: V)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    open class BaseRecyclerHolder<V : ViewBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root)

}