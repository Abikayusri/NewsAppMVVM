package abika.sinau.newsappmvvm.ui.fragment

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.adapters.NewsAdapter
import abika.sinau.newsappmvvm.ui.NewsActivity
import abika.sinau.newsappmvvm.ui.NewsViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    // TODO 7-8: Inisialisasi viewModel
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    // TODO 7-9: Buat function untuk memanggilnya
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        // TODO 10-7: Panggil function setupRecyclerView
        setupRecyclerView()

        // TODO 10-5: Tambahkan onClickListener pada Adapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { // bundle digunakan sebagai pengganti intent
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    // TODO 10-6: Buat function setupRecyclerView
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}