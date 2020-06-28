package abika.sinau.newsappmvvm.adapters

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.model.ArticlesItem
import android.os.strictmode.UntaggedSocketViolation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener: ((ArticlesItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticlesItem) -> Unit){
        onItemClickListener = listener
    }
}