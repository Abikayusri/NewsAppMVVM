package abika.sinau.newsappmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// TODO 4-1: anotate class dengan entity
@Entity(
	tableName = "articles"
)

data class ArticlesItem(
	// TODO 4-2: tambahkan primary key, buat dengan autoGenerate agar primary key bertambah secara otomatis
	@PrimaryKey(autoGenerate = true)
	var id: Int? = null,

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)// TODO 10-1: Tambahkan Serilizable
	: Serializable