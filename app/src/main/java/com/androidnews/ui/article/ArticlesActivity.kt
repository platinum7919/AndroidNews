package com.androidnews.ui.article


import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnews.R
import com.androidnews.common.BaseActivity
import com.androidnews.utils.InjectorUtils

/**
 * A simple detail [Activity] that shows a [User] object (read-only)
 */
class ArticlesActivity : BaseActivity() {


    val parentLayout by lazy {
        findViewById<ConstraintLayout>(R.id.parent)
    }

    private val viewModel by lazy {
        val factory = InjectorUtils.provideArticleViewModelFactory(this)
        ViewModelProviders.of(this, factory).get(ArticlesViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_articles)
        supportActionBar?.setTitle(R.string.articles_title)

        viewModel.getArticlesData().observe(this, Observer {

        })
    }


    override fun onResume() {
        super.onResume()
    }

}