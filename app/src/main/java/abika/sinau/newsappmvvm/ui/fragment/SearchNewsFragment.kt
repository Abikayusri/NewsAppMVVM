package abika.sinau.newsappmvvm.ui.fragment

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.ui.NewsActivity
import abika.sinau.newsappmvvm.ui.NewsViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    // TODO 7-10: Inisialisasi viewModel
    lateinit var viewModel: NewsViewModel

    // TODO 7-11: Buat function untuk memanggilnya
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}