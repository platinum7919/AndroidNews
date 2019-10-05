package com.androidnews.common

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView


abstract class ListRecyclerViewAdapter<K>(val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<RecyclerViewViewHolder<K>>() {

    var itemsDelegate: (() -> List<K>?)? = null

    val items: List<K>? get() = itemsDelegate?.invoke()

    @MainThread
    fun update(itemsDelegate: (() -> List<K>?), notify: Boolean = true) {
        this.itemsDelegate = itemsDelegate
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun getItemAt(position: Int): K? {
        return items?.let { list ->
            if (position in 0 until list.size) list[position] else null
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItemAt(position)
        return if (item == null) 0 else getItemViewType(item)
    }

    abstract fun getItemViewType(item: K): Int


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<K>, position: Int) {
        holder.onBindViewHolder(position, itemCount, getItemAt(position))
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewViewHolder<K>) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow(holder);
    }
}


abstract class RecyclerViewViewHolder<K>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var lastBindedItem: K? = null
    protected var lastBindedPosition: Int? = null
    private var lastBindedTotal: Int? = null

    fun onBindViewHolder(position: Int, total: Int, item: K?) {
        bindView(position, total, item)
        lastBindedItem = item
        lastBindedPosition = position
        lastBindedTotal = total
    }

    protected abstract fun bindView(position: Int, total: Int, item: K?)


    fun onViewAttachedToWindow(holder: RecyclerViewViewHolder<K>) {
        onAttachedToWindow(lastBindedPosition ?: 0, lastBindedTotal ?: 0, lastBindedItem)
    }

    open fun onAttachedToWindow(position: Int, total: Int, item: K?) {

    }
}

