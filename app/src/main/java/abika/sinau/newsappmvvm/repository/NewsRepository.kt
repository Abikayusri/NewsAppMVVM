package abika.sinau.newsappmvvm.repository
import abika.sinau.newsappmvvm.api.RetrofitInstance
import abika.sinau.newsappmvvm.db.ArticleDatabase

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-2: Buat sebuah class repository yang digunakan untuk mensetting bagian API
class NewsRepository (val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}