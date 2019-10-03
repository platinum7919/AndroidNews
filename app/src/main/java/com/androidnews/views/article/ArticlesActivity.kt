package com.androidnews.views.article


import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.androidnews.R
import com.androidnews.common.BaseActivity
import com.androidnews.viewmodel.ArticleViewModel
import com.androidnews.viewmodel.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject




/**
 * A simple detail [Activity] that shows a [User] object (read-only)
 */
class ArticlesActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    val parentLayout by lazy {
        findViewById<ConstraintLayout>(R.id.parent)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ArticleViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_articles)
        supportActionBar?.setTitle(R.string.articles_title)
        Timber.d("viewModel = ${viewModel} parentLayout = ${parentLayout}")



    }


    override fun onResume() {
        super.onResume()
    }

}