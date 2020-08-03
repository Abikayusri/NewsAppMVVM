package abika.sinau.newsappmvvm.repository

import abika.sinau.newsappmvvm.api.RetrofitInstance
import abika.sinau.newsappmvvm.db.ArticleDatabase
import abika.sinau.newsappmvvm.model.ArticlesItem

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-2: Buat sebuah class repository yang digunakan untuk mensetting bagian API
class NewsRepository(val db: ArticleDatabase) {
    // TODO 8-1: Buat sebuah function yang digunakan untuk mendapatkan API dari BreakingNews
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    // TODO 9-1: Buat sebuah function search
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    // TODO 11-1: Buat sebuah function save
    suspend fun upsert(article: ArticlesItem) = db.getArticleDao().upsert(article)

    // TODO 11-1:
    fun getSavedNews() = db.getArticleDao().getAllArticles()

    // TODO 11-1:
    suspend fun deleteArticle(article: ArticlesItem) = db.getArticleDao().deleteArticle(article)
}