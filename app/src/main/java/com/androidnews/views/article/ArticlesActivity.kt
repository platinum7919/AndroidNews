package com.androidnews.views.article


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnews.R
import com.androidnews.common.BaseActivity
import com.androidnews.data.ArticleList
import com.androidnews.viewmodel.ArticleListViewModel
import com.androidnews.viewmodel.ViewModelFactory
import com.androidnews.views.customviews.AsyncLayout
import com.androidnews.views.customviews.MessageAction
import timber.log.Timber
import javax.inject.Inject


/**
 * A simple detail [Activity] that shows a [User] object (read-only)
 */
class ArticlesActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val asyncLayout by lazy {
        findViewById<AsyncLayout>(R.id.asynclayout)
    }

    val loadMoreButton by lazy {
        findViewById<Button>(R.id.button_articles_loadmore)
    }
    val articleList by lazy {
        findViewById<TextView>(R.id.textview_articles_content)
    }


    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ArticleListViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        supportActionBar?.setTitle(R.string.articles_title)
        Timber.d("viewModel = ${viewModel} asyncLayout = ${asyncLayout}")

        viewModel.articleList.observe(this, Observer {
            update(it)
        })

        loadMoreButton.setOnClickListener {
            viewModel.loadData()
        }
        viewModel.loadData()
        asyncLayout.showLoading()
    }


    fun update(result: com.androidnews.services.Result<ArticleList>) {
        if(result.isFetching){
            asyncLayout.showLoading()

        } else if (result.isSuccess) {
            asyncLayout.showDefault()
            articleList.text = StringBuilder().apply {
                result.data?.list?.forEach {
                    append(it.title)
                    append("\r\n")
                }
                append("${result.data?.page ?: 0} of ${result.data?.totalPages ?: 0}")
            }.toString()

        } else {
            val e = result.error!!
            Timber.e(e)
            asyncLayout.showError(e, action = MessageAction(text = getString(R.string.button_retry)) {
                viewModel.loadData()
            })
        }


    }

    override fun onResume() {
        super.onResume()
    }

}