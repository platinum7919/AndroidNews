package com.androidnews.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnews.utils.AppPrefs
import com.androidnews.data.Article
import com.androidnews.data.Articles
import com.pixplicity.easyprefs.library.Prefs

class ArticleViewModel(val ctx: Context) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {


    private val prefs by lazy {
        AppPrefs.getInstance(ctx)
    }

    val articles = MutableLiveData<Articles>()

    init {
       // user.value = prefs.user
        Prefs.getPreferences().registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
//            prefs.KEY_USER -> {
//                user.value = prefs.user ?: emptyUser
//            }
        }
    }

//    fun update(user: User) {
//        prefs.user = user
//    }

}

class ArticleViewModelFactory(val ctx: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(ctx) as T
    }
}