package shkonda.artschools.domain.model.article

data class Articles(
    val articlesList: List<Article>
)

data class Article(
    val id: Long,
    val title: String,
    val text: String,
    val articleCategoryId: Long,
    val artGenreId: Long
)

data class ArtistArticle(
    val artistId: Long,
    val artistName: String,
    val artistImage: String,
    val articleId: Long,
    val title: String,
    val text: String,
    val bioArticleImages: List<BioArticleImage>
)

data class BioArticleImage(
    val imageName: String,
    val imageDescription: String
)