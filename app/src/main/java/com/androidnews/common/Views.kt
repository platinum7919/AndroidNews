package com.androidnews.common

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DimenRes
import com.androidnews.R
import com.bumptech.glide.Glide
import java.util.*


/**
 * Set certain children [View] with tag
 */
fun ViewGroup.setChildrenTags(id: Int, value: Any, where: ((v: View) -> Boolean) = { true }) {
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
fun ViewGroup.getChildren(where: (v: View) -> Boolean): List<View> {
    return mutableListOf<View>().also {
        for (i in 0 until this.childCount) {
            val v = getChildAt(i)
            if (where(v)) {
                it.add(v)
            }
        }
    }.toList()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.loadUrl(ctx: Context, url: String) {
    Glide
        .with(ctx)
        .load(url)
        .thumbnail(Glide.with(ctx).load(com.androidnews.R.drawable.spinner))
        .error(com.androidnews.R.drawable.ic_alert_circle_outline_grey600_36dp)
        .centerCrop()
        .into(this)
}

fun Context.dimenToPx(@DimenRes res: Int): Int {
    return this.resources.dimenToPx(res)
}

fun Resources.dimenToPx(@DimenRes res: Int): Int {
    return getDimension(res).toInt()
}

fun Context.dpToPx(@DimenRes res: Int): Int {
    return this.resources.dpToPx(res)
}

fun Resources.dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun Context.pxToDp(@DimenRes res: Int): Int {
    return this.resources.pxToDp(res)
}

fun Resources.pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun View.margin(startPx: Int? = null, topPx: Int? = null, bottomPx: Int? = null, endPx: Int? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let { lp ->
        startPx?.let {
            lp.marginStart = it
        }
        endPx?.let {
            lp.marginEnd = it
        }

        topPx?.let {
            lp.topMargin = it
        }

        bottomPx?.let {
            lp.bottomMargin = it
        }
        requestLayout()
    }
}


fun Date.getPastDurationText(ctx: Context): String {
    val now = Calendar.getInstance()
    val target = Calendar.getInstance().also {
        it.time = this
    }
    if (target.after(now)) {
        return "Future..."
    }
    val daysApart = now.get(Calendar.DAY_OF_YEAR) - target.get(Calendar.DAY_OF_YEAR)
    return (if (now.get(Calendar.YEAR) == target.get(Calendar.YEAR)) {
        if (daysApart == 0) {
            val hoursApart = now.get(Calendar.HOUR_OF_DAY) - target.get(Calendar.HOUR_OF_DAY)
            if (hoursApart == 0) {
                val minsApart = now.get(Calendar.MINUTE) - target.get(Calendar.MINUTE)
                ctx.getString(R.string.x_minutes_ago, minsApart.toString())
            } else {
                ctx.getString(R.string.x_hours_ago, hoursApart.toString())
            }
        } else if (daysApart == 1) {
            ctx.getString(R.string.yesterday)
        } else if (daysApart <= 31) {
            ctx.getString(R.string.x_days_ago, daysApart.toString())
        } else {
            ctx.getString(R.string.x_months_ago, (now.get(Calendar.MONTH) - target.get(Calendar.MONTH)).toString())
        }
    } else {
        ctx.getString(R.string.x_years_ago, (now.get(Calendar.YEAR) - target.get(Calendar.YEAR)).toString())
    })
}
