package shkonda.artschools.data.data_source.articles

import shkonda.artschools.data.data_source.articles.api.ArticleApi
import shkonda.artschools.data.data_source.articles.entity.ArticlesDto
import shkonda.artschools.data.data_source.articles.entity.ArtistArticleDto
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val api: ArticleApi): ArticleRemoteDataSource {
    override suspend fun getAllArticleByGenreId(genreId: Long): ArticlesDto =
        api.getAllArticleByGenreId(genreId)

    override suspend fun getBioArticleByArtistId(artistId: Long): ArtistArticleDto =
        api.getBioArticleByArtistId(artistId)
}