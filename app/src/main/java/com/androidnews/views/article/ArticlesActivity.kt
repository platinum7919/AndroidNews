package com.androidnews.views.article


import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnews.R
import com.androidnews.common.BaseActivity
import com.androidnews.viewmodel.ArticleListViewModel
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

    val loadMoreButton by lazy {
        findViewById<Button>(R.id.button_articles_loadmore)
    }


    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ArticleListViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_articles)
        supportActionBar?.setTitle(R.string.articles_title)
        Timber.d("viewModel = ${viewModel} parentLayout = ${parentLayout}")

        viewModel.articleList.observe(this, Observer {
            if(it.isSuccess) {
                val data = it.data!!
                Timber.d("page = ${data.page} of ${data.totalPages}")
                data.list.forEach {
                    Timber.d("article = ${it.title}")
                }
            }else{
                Timber.e(it.error)
            }
        })

        loadMoreButton.setOnClickListener {
            viewModel.loadMore()
        }

        viewModel.onCreate()

    }


    override fun onResume() {
        super.onResume()
    }

}