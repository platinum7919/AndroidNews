package com.androidnews.views.article

import com.androidnews.common.BaseActivity
import com.androidnews.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * A simple detail [Activity] that shows a [User] object (read-only)
 */
class ViewArticleActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

}