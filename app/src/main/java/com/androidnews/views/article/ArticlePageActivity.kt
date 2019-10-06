package com.androidnews.views.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnews.common.*
import com.androidnews.data.Article
import com.androidnews.data.ArticlePage
import com.androidnews.viewmodel.ArticlePageViewModel
import com.androidnews.viewmodel.ViewModelFactory
import com.androidnews.views.EXTRA_ARTICLE_ID
import com.androidnews.views.customviews.AsyncLayout
import com.androidnews.views.customviews.MessageAction
import timber.log.Timber
import javax.inject.Inject


/**
 * A simple detail [Activity] that shows a [User] object (read-only)
 */
class ArticlePageActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    /**
     * A helper [ViewGroup] custom view that helps render various states of
     * an [com.androidnews.repository.Result] object
     */
    private val asyncLayout by lazy {
        findViewById<AsyncLayout>(com.androidnews.R.id.asynclayout)
    }

    private val recycleView by lazy {
        findViewById<RecyclerView>(com.androidnews.R.id.recyclerview_articles).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }


    /**
     * [RecyclerView.Adapter] for [recycleView]
     */
    private val adapter by lazy {
        ArticleAdapter(layoutInflater,
            loadMore = {
                viewModel.loadData()
            },
            onItemClicked = {
                onArticleClicked(it)
            }
        ).apply {
            recycleView.adapter = this
        }
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ArticlePageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.androidnews.R.layout.activity_articles)
        supportActionBar?.setTitle(com.androidnews.R.string.articles_title)
        Timber.d("viewModel = ${viewModel} json = ${json}")

        viewModel.articlePage.observe(this, Observer {
            update(it)
        })

        viewModel.loadData()
        asyncLayout.showLoading()

    }


    /**
     * Update the UI based on [com.androidnews.repository.Result] object values
     */
    private fun update(result: com.androidnews.repository.Result<ArticlePage>) {
        if (result.isFetching) {
            // loading
            if (adapter.itemCount == 0) {
                asyncLayout.showLoading()
            }

        } else if (result.isSuccess) {
            // success
            asyncLayout.showDefault()
            val list = result.data?.list
            if (list?.size == 0) {
                // empty
                asyncLayout.showMessage().apply {
                    setMessage(
                        getString(com.androidnews.R.string.message_no_articles_found),
                        action = MessageAction(getString(com.androidnews.R.string.button_retry)) {
                            viewModel.loadData()
                        })
                }
            } else {
                // has content
                adapter.update({ list })
            }

        } else {
            Timber.e(result.error)
            asyncLayout.showError(
                result.error,
                action = MessageAction(text = getString(com.androidnews.R.string.button_retry)) {
                    viewModel.loadData()
                })
        }
    }

    /**
     * When an [Article] is clicked
     *
     */
    fun onArticleClicked(article: Article) {
        startActivity(Intent(this, ViewArticleActivity::class.java).apply {
            putExtra(EXTRA_ARTICLE_ID, article.id)
        })
    }

}


/**
 * An [ListRecyclerViewAdapter] implemention that helps to render [Article] object
 * on [RecyclerView]
 */
class ArticleAdapter(
    layoutInflater: LayoutInflater,
    val loadMore: (() -> Unit)?,
    val onItemClicked: ((Article) -> Unit)?
) :
    ListRecyclerViewAdapter<Article>(layoutInflater = layoutInflater) {
    val ctx: Context get() = layoutInflater.context

    init {

    }

    override fun getItemViewType(item: Article): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<Article> {
        return ViewHolder(parent)
    }

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerViewViewHolder<Article>(
            layoutInflater.inflate(
                com.androidnews.R.layout.listitem_article,
                parent,
                false
            )
        ) {

        val card by lazy {
            itemView.findViewById<CardView>(com.androidnews.R.id.cardview_article)
        }

        val thumbnailImage by lazy {
            itemView.findViewById<ImageView>(com.androidnews.R.id.imageview_article_thumbnail)
        }

        val headerText by lazy {
            itemView.findViewById<TextView>(com.androidnews.R.id.textview_article_header)
        }

        val titleText by lazy {
            itemView.findViewById<TextView>(com.androidnews.R.id.textview_article_title)
        }

        val descriptionText by lazy {
            itemView.findViewById<TextView>(com.androidnews.R.id.textview_article_description)
        }

        val dateText by lazy {
            itemView.findViewById<TextView>(com.androidnews.R.id.textview_article_date)
        }


        init {
            card.setOnClickListener {
                lastBindedItem?.let { item ->
                    onItemClicked?.invoke(item)
                }
            }
        }

        override fun bindView(position: Int, total: Int, item: Article?) {
            item ?: return

            item.source?.name?.let {
                headerText.text = it
                headerText.visible()
            } ?: run {
                headerText.gone()
            }

            titleText.text = item.title ?: ""
            descriptionText.text = item.description ?: ""
            thumbnailImage.loadUrl(ctx, item.urlToImage ?: "")
            item.publishedAt?.let {
                dateText.visible()
                dateText.text = it.getPastDurationText(ctx)

            } ?: run {
                dateText.gone()
            }

            card.margin(
                topPx = ctx.dimenToPx(if (position == 0) com.androidnews.R.dimen.margin_4x else com.androidnews.R.dimen.margin_2x),
                bottomPx = ctx.dimenToPx(if (position >= itemCount - 1) com.androidnews.R.dimen.margin_4x else com.androidnews.R.dimen.margin_2x)
            )
        }

        override fun onAttachedToWindow(position: Int, total: Int, item: Article?) {
            // load more on last item
            if (position == total - 1) {
                loadMore?.invoke()
            }
        }
    }

}