package abika.sinau.newsappmvvm.db

import abika.sinau.newsappmvvm.model.ArticlesItem
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */

// TODO 4-3: Buat kelas DAO baru untuk mendefinisikan fungsi yang akan digunakan
@Dao
interface ArticleDAO {

    // TODO 4-4: Anotate function untuk insert.
    // Masukkan onConflict yang akan berfungsi ketika terjadi data yang sama yang diinputkan
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // karena menggunakan coroutine dapat menggunakan "suspend". Bertipe long untuk menyesuaikan ID yang dimasukkan
    suspend fun upsert(article: ArticlesItem): Long

    // TODO 4-5: Masukkan query yang digunakan ke dalam database
    @Query("SELECT * FROM articles") // articles adalah nama database yang dibuat
    fun getAllArticles(): LiveData<List<ArticlesItem>>

    // TODO 4-5: Buat function untuk menghapus data
    @Delete
    suspend fun deleteArticle(article: ArticlesItem)
}