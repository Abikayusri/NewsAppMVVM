package abika.sinau.newsappmvvm.ui.fragment

import abika.sinau.newsappmvvm.R
import abika.sinau.newsappmvvm.adapters.NewsAdapter
import abika.sinau.newsappmvvm.ui.NewsActivity
import abika.sinau.newsappmvvm.ui.NewsViewModel
import abika.sinau.newsappmvvm.util.Resource
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_breaking_news.*

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    // TODO 7-7: inisialisasi viewModel terlebih dahulu
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "BreakingNewsFragment"

    // TODO 7-6: Buat sebuah function untuk memanggil ViewModel di Fragment ini
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // copy bagian ini pastekan di SavedNewsFragment
        viewModel = (activity as NewsActivity).viewModel

        // TODO 8-6: panggil functionnya di sini
        setupRecyclerView()

        // TODO 10-3: Tambahkan onClickListener pada Adapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { // bundle digunakan sebagai pengganti intent
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        // TODO 8-7: untuk memanggil breaking news live data kita perlu memanggil observe
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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

    // TODO 8-5: Buat sebuah function untuk menset newsAdapter
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    // TODO 8-8: buat sebuah function untuk progress bar
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    // TODO 8-8: buat sebuah function untuk progress bar
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
}