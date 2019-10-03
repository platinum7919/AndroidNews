package com.androidnews.views.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class AsyncLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {

    }


    fun setLoading() {

    }


    fun setDefault() {

    }


    fun setMessage(message: String) {

    }


    fun setError() {

    }
}


class DefaultAsyncLayoutDelegate(
    val context : Context
) {

}


interface AsyncLayoutDelegate {
    fun createLoadingView(): LoadingView
    fun createMessageView(): MessageView
}


interface LoadingView {
    fun getView(): View
}

interface MessageView {
    fun setImage(drawable: () -> Drawable)
    fun setMessage(text: String, action: MessageAction)
    fun getView(): View
}

class MessageAction(val text: String, action: () -> Unit)
