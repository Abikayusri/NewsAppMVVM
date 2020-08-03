package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.NewsApplication
import abika.sinau.newsappmvvm.model.ArticlesItem
import abika.sinau.newsappmvvm.model.NewsResponse
import abika.sinau.newsappmvvm.repository.NewsRepository
import abika.sinau.newsappmvvm.util.Resource
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import kotlin.reflect.typeOf

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-1: Buat sebuah class viewModel dan panggil repository
class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
//) : ViewModel() {
// TODO 13-2: Rubah menjadi AndroidViewModel -> karena kita ingin memanggil aplicationContext
) : AndroidViewModel(app) {

    // TODO 8-2: buat sebuah liveDataObject
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1 // karena kita akan memanage halaman di viewModel. jika kita menaruhnya
    // implementasikan beberapa logic yang akan meresponse kita
    // tidak akan hancur kita merotasinya. Saat ini kita deklarasikan pagenya = 1, nanti kita akan
    // di fragment halaman terkini akan selalu direset ketika kita merotasi device dan viewModel

    // TODO 12-1:
    var breakingNewsResponse: NewsResponse? = null
    var searchNewsResponse: NewsResponse? = null

    // TODO 9-3: buat juga sebuah liveDataObject
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1 // karena kita akan memanage halaman di viewModel. jika kita menaruhnya

    // TODO 8-9: karena kita belum memanggil function getBreakingNews, kita perlu inisialisasi di sini
    init {
        getBreakingNews("us")
    }

    // TODO 8-3: implement function kita. dikarenakan repository menggunakan suspend function dari
    //  coroutine yang akan dijalankan di fragment dan kita tidak mengiginkan hal itu. karenanya kita panggil di sini
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
//        breakingNews.postValue(Resource.Loading())

        // TODO 12-11: Panggil methodnya di sini, method di atas dihapus
        safeBreakingNewsCall(countryCode)

        // TODO 12-9 Dicut ditaro di safeBreakingNewsCall
//        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
//        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    // TODO 9-4: buat sebuah function untuk memanggil searchFunction
    fun searchNews(searchQuery: String) = viewModelScope.launch {
//        searchNews.postValue(Resource.Loading())
//
//        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
//        searchNews.postValue(handleSearchNewsResponse(response))

        // TODO 12-11: Panggil methodnya di sini
        safeSearchNewsCall(searchQuery)
    }

    // TODO 8-4: buat sebuah response function yang berfungsi untuk memberikan response ketika response success maupun error
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // TODO 12-2:
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles

                    newArticles?.let { oldArticles?.addAll(it) }
                }


                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // TODO 9-2: buat sebuah response function yang berfungsi untuk memberikan response untuk melakukan search
    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // TODO 12-4:
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles

                    newArticles?.let { oldArticles?.addAll(it) }
                }


                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // TODO 11-2: buat sebuah response function yang berfungsi untuk memberikan response untuk save data
    fun saveArticle(articlesItem: ArticlesItem) = viewModelScope.launch {
        newsRepository.upsert(articlesItem)
    }

    // tidak memerlukan viewModelScope karena tidak menggunakan suspend function
    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(articlesItem: ArticlesItem) = viewModelScope.launch {
        newsRepository.deleteArticle(articlesItem)
    }

    // TODO 12-8: Buat function untuk menampilkan data breaking news secara aman
    private suspend fun safeBreakingNewsCall(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleSearchNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    // TODO 12-10: Buat fungsi yang sama untuk fitur search
    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleBreakingNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    // TODO 13-1: buat fungsi untuk check internet
    private fun hasInternetConnection(): Boolean {
        // TODO 13-5: buat sebuah variable connectivity
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // digunakan untuk SDK diatas marshmellow atau di atas 23
            // akan ada error di sini, tambahkan permission access di manifest terlebih dahulu
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {// digunakan untuk SDK dibawah marshmellow atau di bawah 23
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}