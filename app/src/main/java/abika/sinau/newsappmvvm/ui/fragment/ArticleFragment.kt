package abika.sinau.newsappmvvm.ui.fragment

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.model.ArticlesItem
import abika.sinau.newsappmvvm.ui.NewsActivity
import abika.sinau.newsappmvvm.ui.NewsViewModel
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class ArticleFragment : Fragment(R.layout.fragment_article) {
    // TODO 7-12: Inisialisasi viewModel
    lateinit var viewModel: NewsViewModel

    // TODO 10-8: Tambahkan argument untuk menerima data yang diparsing
    val args: ArticleFragmentArgs by navArgs()

    // TODO 7-13: Buat function untuk memanggilnya
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        // TODO 10-9: buat variable baru dengan nama article untuk memanggil args
        val article = args.article

        // TODO 10-10: buat fungsi untuk memanggil webView
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        // TODO 11-3: baut fungsi untuk melakukan eksekusi save data
        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article Saved Succesfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}