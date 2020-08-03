package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.repository.NewsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-3: Buat sebuah viewModelProviderFactory yang digunakan mendefine newsRepository yang nantinya akan dipanggil di viewModel
class NewsViewModelProviderFactory (
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}