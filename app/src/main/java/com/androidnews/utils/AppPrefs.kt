package com.androidnews.utils

import android.content.Context
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs

class AppPrefs(ctx: Context) {

    companion object {
        @Volatile
        private var instance: AppPrefs? = null

        fun getInstance(ctx: Context): AppPrefs {
            return instance ?: synchronized(this) {
                instance ?: AppPrefs(ctx).also {
                    instance = it
                }
            }
        }
    }

    init {
        Prefs.Builder()
            .setContext(ctx)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(ctx.packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }


//    val KEY_USER = "KEY_USER1"

//    var user: User?
//        get() {
//            return Prefs.getString(KEY_USER, null)?.let {
//                Json.toObject<User>(it)
//            }
//        }
//        set(value) {
//            Prefs.putString(KEY_USER, if (value == null) null else Json.toJson(value))
//        }


}