package abika.sinau.newsappmvvm.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)