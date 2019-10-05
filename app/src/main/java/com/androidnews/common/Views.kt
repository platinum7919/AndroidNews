package com.androidnews.common

import android.view.View
import android.view.ViewGroup

/**
 * Set certain children [View] with tag
 */
fun ViewGroup.setChildrenTags(id: Int, value : Any, where: ((v: View) -> Boolean) = { true }) {
    getChildren(where).forEach {
        it.setTag(id, value)
    }
}

/**
 * Remove child [View] if [where] returns true
 */
fun ViewGroup.removeChildren(where: (v: View) -> Boolean) {
    getChildren(where).forEach {
        removeView(it)
    }
}

/**
 * Children [View] are returned as [List]
 */
fun ViewGroup.getChildren(where: (v: View) -> Boolean) : List<View>{
    return mutableListOf<View>().also {
        for(i in 0 until this.childCount){
            val v = getChildAt(i)
            if(where(v)) {
                it.add(v)
            }
        }
    }.toList()
}