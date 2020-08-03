package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.repository.NewsRepository
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-3: Buat sebuah viewModelProviderFactory yang digunakan mendefine newsRepository yang nantinya akan dipanggil di viewModel
class NewsViewModelProviderFactory (
    // TODO 12-12 Tambahkan parameter application
    val app: Application,

    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // TODO 12-13: Tambahkan juga methodnya di sini
        return NewsViewModel(app, newsRepository) as T
    }
}