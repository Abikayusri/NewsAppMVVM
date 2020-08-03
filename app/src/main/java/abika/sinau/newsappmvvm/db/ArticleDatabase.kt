package abika.sinau.newsappmvvm.db

import abika.sinau.newsappmvvm.model.ArticlesItem
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */

// TODO 5-1: Buat sebuah class database untuk ROOM
// TODO 5-2: Buat sebuah anotasi database agar ROOm mengetahui bahwa ini adalah databasenya
@Database(
    entities = [ArticlesItem::class], // masukkan entitasnya
    version = 1 // masukkan version yang digunakan untuk menamai jika ada perubahan
)

// TODO 5-6: Tambahkan anotasi typeConverter yang telah kita buat sebelumnya
@TypeConverters(Converters::class)
// TODO 5-3: Buat sebuah abstract class database dan inherit dari RoomDatabase
abstract class ArticleDatabase : RoomDatabase() {

    // TODO 5-4: Buat sebuah function untuk mengembalikkan article DAO
    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile // artinya threads lain dapat segera melihat ketika thread ini berubah
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        // invoke dipanggil ketika kita membuat proses terhadap database kita
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        } // blok di atas berfungsi segala sesuatu yang terjadi di dalam blok di atas tidak dapat
        // diakses oleh thread lain dalam waktu yang bersamaan. jadi kita perlu meyakinkan kita tidak mensetnya dalam
        // thread lain yang men set proses tersebut


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db" // nama dari database
            ).build()
    }
}