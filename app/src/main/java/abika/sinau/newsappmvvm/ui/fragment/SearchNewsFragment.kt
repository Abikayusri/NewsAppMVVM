package abika.sinau.newsappmvvm.ui.fragment

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.adapters.NewsAdapter
import abika.sinau.newsappmvvm.ui.NewsActivity
import abika.sinau.newsappmvvm.ui.NewsViewModel
import abika.sinau.newsappmvvm.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import abika.sinau.newsappmvvm.util.Resource
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    // TODO 7-10: Inisialisasi viewModel
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "SearchNewsFragment"

    // TODO 7-11: Buat function untuk memanggilnya
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        // TODO 9-8: Panggil functionnya+
        setupRecyclerView()

        // TODO 10-4: Tambahkan onClickListener pada Adapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { // bundle digunakan sebagai pengganti intent
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        // TODO 9-9: Untuk menambahkan delay ketika menampilkan data kita bisa menggunakan function
        //  yang terdapat pada coroutine
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        // TODO 9-5: Tambahkan observe untuk searchNews
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    // TODO 9-6: Tambahkan function progressBar
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    // TODO 9-6: Tambahkan function progressBar
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    // TODO 9-7: Tambahkan function recyclerView
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}