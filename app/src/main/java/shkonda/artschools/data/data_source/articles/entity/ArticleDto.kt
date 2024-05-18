package shkonda.artschools.data.data_source.articles.entity

import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("articles")
    val articlesList: List<Article>
)
data class Article(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("articleCategory")
    val articleCategoryId: Long,
    @SerializedName("artGenre")
    val artGenreId: Long
)

data class ArtistArticleDto(
    @SerializedName("id")
    val artistId: Long,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistImage")
    val artistImage: String,
    @SerializedName("articleId")
    val articleId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("articleImages")
    val articleImages: List<BioArticleImageDto>
)

data class BioArticleImageDto(
    @SerializedName("imageName")
    val imageName: String,
    @SerializedName("imageDescription")
    val imageDescription: String
)