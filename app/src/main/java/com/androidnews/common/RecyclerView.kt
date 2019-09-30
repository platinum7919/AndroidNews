package com.androidnews.common

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Extension function to set [RecyclerView] as a vertical list
 */
fun RecyclerView.setAsVerticalList(ctx: Context): LinearLayoutManager {
    return LinearLayoutManager(ctx, RecyclerView.VERTICAL, false).also {
        layoutManager = it
    }
}