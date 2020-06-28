package abika.sinau.newsappmvvm.repository
import abika.sinau.newsappmvvm.api.RetrofitInstance
import abika.sinau.newsappmvvm.db.ArticleDatabase

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class NewsRepository (val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}