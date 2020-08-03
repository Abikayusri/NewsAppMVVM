package abika.sinau.newsappmvvm.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	// TODO 12-3: Rubah menjadi MutableList
	@field:SerializedName("articles")
	val articles: MutableList<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)