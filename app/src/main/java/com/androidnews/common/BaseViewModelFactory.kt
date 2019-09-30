package com.androidnews.common

import android.content.Context
import androidx.lifecycle.ViewModelProvider

open class BaseViewModelFactory(val ctx : Context) : ViewModelProvider.NewInstanceFactory() {

}