package com.androidnews.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.androidnews.appCtx

open class BaseFragment : Fragment() {
    val ctx: Context
        get() {
            return activity?.applicationContext ?: appCtx
        }
}