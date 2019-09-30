package com.androidnews.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView


abstract class ListRecyclerViewAdapter<K : RecyclerViewItem>(layoutInflater: LayoutInflater) :
    RecyclerViewAdapter<K>(layoutInflater) {

    var itemsDelegate: (() -> List<K>?)? = null

    val items : List<K>? get() = itemsDelegate?.invoke()

    @MainThread
    fun update(itemsDelegate: (() -> List<K>?), notify: Boolean = true) {
        this.itemsDelegate = itemsDelegate
        if (notify) {
            notifyDataSetChanged()
        }
    }

    override fun getItemAt(position: Int): K? {
        return items?.let { list ->
            if (position in 0 until list.size) list[position] else null
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}

abstract class RecyclerViewAdapter<K : RecyclerViewItem>(val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<RecyclerViewViewHolder<K>>() {

    val ctx: Context
        get() {
            return layoutInflater.context
        }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<K>, position: Int) {
        val item = getItemAt(position) ?: return
        holder.onBindViewHolder(position, itemCount, item)
    }

    abstract fun getItemAt(position: Int): K?

}


abstract class RecyclerViewViewHolder<K : RecyclerViewItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindViewHolder(position: Int, total: Int, item: K)
}


interface RecyclerViewItem {
    val id: String
    val viewType: Int
}