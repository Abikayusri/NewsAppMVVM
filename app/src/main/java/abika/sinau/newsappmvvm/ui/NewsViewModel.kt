package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.repository.NewsRepository
import androidx.lifecycle.ViewModel

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class NewsViewModel (
    val newsRepository: NewsRepository
) : ViewModel() {
}