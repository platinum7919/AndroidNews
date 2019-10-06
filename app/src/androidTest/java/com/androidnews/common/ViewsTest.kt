package com.androidnews.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ViewsTest : AndroidTest() {


    @Test
    fun test_visbility() {
        val layout = LayoutInflater.from(ctx).inflate(R.layout.layout_test, null, false)

        layout.visible()
        assertEquals(View.VISIBLE, layout.visibility)
        layout.gone()
        assertEquals(View.GONE, layout.visibility)
    }


    @Test
    fun test_margin() {
        val layout = LayoutInflater.from(ctx).inflate(R.layout.layout_test, null, false)
        val textview = layout.findViewById<TextView>(R.id.textview_1)
        textview.margin(startPx = 1, topPx = 2, bottomPx = 3, endPx = 4)


        val layoutParams = textview.layoutParams as? ViewGroup.MarginLayoutParams
        assertEquals(true, layoutParams is ViewGroup.MarginLayoutParams)


        assertEquals(1, layoutParams?.marginStart ?: 0)
        assertEquals(2, layoutParams?.topMargin ?: 0)
        assertEquals(3, layoutParams?.bottomMargin ?: 0)
        assertEquals(4, layoutParams?.marginEnd ?: 0)
    }


    @Test
    fun test_children() {
        var layout = LayoutInflater.from(ctx).inflate(R.layout.layout_test, null, false) as ViewGroup
        assertEquals(2, layout.getChildren { true }.size)

        var textview1 = layout.findViewById<TextView>(R.id.textview_1)
        var textview2 = layout.findViewById<TextView>(R.id.textview_2)

        layout.setChildrenTags(R.id.is_default, true, where = { it.id == R.id.textview_1 })

        assertEquals(true, textview1.getTag(R.id.is_default))
        assertEquals(null, textview2.getTag(R.id.is_default))


        layout.removeChildren {
            it.getTag(R.id.is_default) != true
        }

        assertEquals(1, layout.getChildren { true }.size)
        assertEquals(null, layout.findViewById<TextView>(R.id.textview_2))

    }






    @Test
    fun test_getStringPastDuration() {
        var dateCal = Calendar.getInstance().apply {
            add(Calendar.DATE, -1)
        }
        assertEquals(ctx.getString(R.string.yesterday), ctx.getStringPastDuration(dateCal.time))

        dateCal = Calendar.getInstance().apply {
            add(Calendar.HOUR, -2)
        }
        assertEquals(ctx.getString(R.string.x_hours_ago, 2), ctx.getStringPastDuration(dateCal.time))

        dateCal = Calendar.getInstance().apply {
            add(Calendar.DATE, -3)
        }
        assertEquals(ctx.getString(R.string.x_days_ago, 3), ctx.getStringPastDuration(dateCal.time))

        dateCal = Calendar.getInstance().apply {
            add(Calendar.MONTH, -4)
        }
        assertEquals(ctx.getString(R.string.x_months_ago, 4), ctx.getStringPastDuration(dateCal.time))

        dateCal = Calendar.getInstance().apply {
            add(Calendar.YEAR, -5)
        }
        assertEquals(ctx.getString(R.string.x_years_ago, 5), ctx.getStringPastDuration(dateCal.time))
    }
}
