package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.db.ArticleDatabase
import abika.sinau.newsappmvvm.repository.NewsRepository
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    // TODO 7-4: panggil viewModel di MainActivity

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // TODO 7-5: inisialisasi newsRepository dan masukkan database(ROOM) yang kita buat
        val newsRepository = NewsRepository(ArticleDatabase(this))
        // TODO 12-14: Panggil juga method applicationnya di sini
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}