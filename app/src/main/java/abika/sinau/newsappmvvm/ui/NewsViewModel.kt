package abika.sinau.newsappmvvm.ui

import abika.sinau.newsappmvvm.model.ArticlesItem
import abika.sinau.newsappmvvm.model.NewsResponse
import abika.sinau.newsappmvvm.repository.NewsRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import abika.sinau.newsappmvvm.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-1: Buat sebuah class viewModel dan panggil repository
class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    // TODO 8-2: buat sebuah liveDataObject
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1 // karena kita akan memanage halaman di viewModel. jika kita menaruhnya
    // implementasikan beberapa logic yang akan meresponse kita
    // tidak akan hancur kita merotasinya. Saat ini kita deklarasikan pagenya = 1, nanti kita akan
    // di fragment halaman terkini akan selalu direset ketika kita merotasi device dan viewModel

    // TODO 9-3: buat juga sebuah liveDataObject
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsPage = 1 // karena kita akan memanage halaman di viewModel. jika kita menaruhnya

    // TODO 8-9: karena kita belum memanggil function getBreakingNews, kita perlu inisialisasi di sini
    init {
        getBreakingNews("us")
    }

    // TODO 8-3: implement function kita. dikarenakan repository menggunakan suspend function dari
    //  coroutine yang akan dijalankan di fragment dan kita tidak mengiginkan hal itu. karenanya kita panggil di sini
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())

        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    // TODO 9-4: buat sebuah function untuk memanggil searchFunction
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())

        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    // TODO 8-4: buat sebuah response function yang berfungsi untuk memberikan response ketika response success maupun error
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    // TODO 9-2: buat sebuah response function yang berfungsi untuk memberikan response untuk melakukan search
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
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
}