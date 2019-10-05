package com.androidnews.views.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.androidnews.R
import com.androidnews.common.getChildren
import com.androidnews.common.getUserMessage
import com.androidnews.common.removeChildren


/**
 * A help [FrameLayout] that let you temporary hide the default child [view]
 *
 * You can change the look and feel by setting your own [AsyncLayoutDelegate] in [delegate]
 */
class AsyncLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    var delegate: AsyncLayoutDelegate = DefaultAsyncLayoutDelegate()

    /**
     * Set the view to a "loading" state
     */
    fun showLoading(): LoadingView {
        removeTempView()
        setDefaultViewVisible(false)
        return delegate.createLoadingView(context).also {
            addView(it.view.also { v ->
                v.setTag(R.id.is_temp, true)
            })
        }
    }

    private fun setDefaultViewVisible(visible: Boolean) {
        getChildren {
            getTag(R.id.is_temp) != true
        }.forEach {
            it.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }


    private fun removeTempView() {
        removeChildren { v ->
            v.getTag(R.id.is_temp) == true
        }
    }

    /**
     * Set the view to the "default" state
     */
    fun showDefault() {
        removeTempView()
        setDefaultViewVisible(true)
    }


    /**
     * Set the view to show a message state
     */
    fun showMessage(): MessageView {
        removeTempView()
        setDefaultViewVisible(false)
        return delegate.createMessageView(context).also {
            addView(it.view.also { v ->
                v.setTag(R.id.is_temp, true)
            })
        }
    }

    /**
     * Set the view to show a message that describes the [Throwable]
     */
    fun showError(error: Throwable?, action: MessageAction): MessageView {
        return showMessage().also {
            it.setMessage(text = error?.getUserMessage(context) ?: "???", action = action)
        }
    }

}


class DefaultAsyncLayoutDelegate : AsyncLayoutDelegate {
    override fun createLoadingView(ctx: Context): LoadingView {
        return object : LoadingView {
            override val view: View by lazy {
                LayoutInflater.from(ctx).inflate(R.layout.loading_view, null, false)
            }
        }
    }

    override fun createMessageView(ctx: Context): MessageView {
        return object : MessageView {
            override fun setMessage(text: String, action: MessageAction) {
                view.findViewById<TextView>(R.id.textview_message_text).text = text
                view.findViewById<TextView>(R.id.button_message_action).also {
                    it.text = action.text
                    it.setOnClickListener {
                        action.action()
                    }
                }
            }

            override fun setImage(drawable: () -> Drawable) {
                view.findViewById<ImageView>(R.id.imageview_message_image).setImageDrawable(drawable())
            }

            override val view: View by lazy {
                LayoutInflater.from(ctx).inflate(R.layout.message_view, null, false)
            }
        }
    }
}


interface AsyncLayoutDelegate {
    fun createLoadingView(ctx: Context): LoadingView
    fun createMessageView(ctx: Context): MessageView
}


interface LoadingView {
    val view: View
}

interface MessageView {
    fun setImage(drawable: () -> Drawable)
    fun setMessage(text: String, action: MessageAction)
    val view: View
}

class MessageAction(val text: String, val action: () -> Unit)
