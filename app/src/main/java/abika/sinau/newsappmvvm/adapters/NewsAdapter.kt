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
// TODO 6-1: Buat sebuah class adapters
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // TODO 6-2: buat sebuah diffUtils. DiffUtils berfungsi untuk menampilkan data dengan mudah
    //  agar tidak terjadi double data saat ditampilkan
    private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        // ketika item sama. masukkan sesuatu yang unik yang memiliki isi sama seperti id/url
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        // membandingkan content ketika terjadi kesamaan
        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    // TODO 6-3: Masukkan async list differ. list differ merupakan tools yang digunakan untuk membandingkan
    //  2 list dan mengkalkulasikan perbedaannya. karena async ini akan dijalankan di background
    val differ = AsyncListDiffer(this, differCallback)

    // TODO 6-4: Implement object seperti biasa
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        // karena kita menggunakan differ. kita panggil isi list differ di sini
        return differ.currentList.size
    }

    // TODO 6-5: Buat sebuah fungsi ketika item diklik
    private var onItemClickListener: ((ArticlesItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            // TODO 6-5: Buat sebuah fungsi ketika item diklik
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setOnItemClickListener(listener: (ArticlesItem) -> Unit) {
        onItemClickListener = listener
    }
}