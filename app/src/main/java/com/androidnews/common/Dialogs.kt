package com.androidnews.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.androidnews.R
import timber.log.Timber

object Dialogs {

    /**
     * Show a list of dialog options
     */
    fun <Key : Any> showAlertDialogOptions(
        activity: Activity,
        title: String?,
        options: List<Pair<Key, CharSequence>>,
        onSelected: (Key) -> Unit,
        onDismiss: () -> Unit = {}
    ): Dialog {
        return AlertDialog.Builder(activity).apply {
            title?.let {
                setTitle(it)
            }

            setAdapter(createListAdapter(activity.layoutInflater, options)) { dialog, which ->
                val selectedKey = options.get(which).first
                onSelected(selectedKey)
                dialog.dismissSafely()
            }

            setOnDismissListener {
                onDismiss()
            }

        }.create().also {
            if (!activity.isFinishing) it.show()
        }
    }

    /**
     * Only one type of ViewHolder
     */
    private fun <Key : Any> createListAdapter(
        layoutInflater: LayoutInflater,
        options: List<Pair<Key, CharSequence>>
    ): android.widget.ListAdapter {

        return object : BaseAdapter() {

            // Dialog options should be limited anyway
            @SuppressLint("ViewHolder")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                return layoutInflater.inflate(R.layout.layout_list_item_dialog_selection_option, parent, false).apply {
                    findViewById<TextView>(R.id.textview)?.let {
                        it.text = options.get(position).second
                    }
                }
            }

            override fun getItem(position: Int): Any {
                return options.get(position)
            }

            override fun getItemId(position: Int): Long {
                return options.get(position).first.hashCode().toLong()
            }

            override fun getCount(): Int {
                return options.size
            }

        }
    }
}

fun DialogInterface.dismissSafely() {
    try {
        dismiss()
    } catch (t: Throwable) {
        Timber.w(t)
    }
}


fun Dialog.dismissSafely() {
    try {
        if (isShowing)
            dismiss()
    } catch (t: Throwable) {
        Timber.w(t)
    }
}
