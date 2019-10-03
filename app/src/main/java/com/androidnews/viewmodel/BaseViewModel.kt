package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnews.App
import com.androidnews.data.Article
import javax.inject.Inject


open class BaseViewModel(val app : Application) : AndroidViewModel(app) {


}

