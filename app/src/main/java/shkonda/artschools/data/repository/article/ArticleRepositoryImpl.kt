package shkonda.artschools.data.repository.article

import shkonda.artschools.data.data_source.articles.ArticleRemoteDataSource
import shkonda.artschools.data.mappers.toArticle
import shkonda.artschools.data.mappers.toBioArtistArticle
import shkonda.artschools.domain.model.article.Articles
import shkonda.artschools.domain.model.article.ArtistArticle
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val remoteDataSource: ArticleRemoteDataSource) : ArticleRepository {
    override suspend fun getAllArticleByGenreId(genreId: Long): Articles =
        remoteDataSource.getAllArticleByGenreId(genreId).toArticle()

    override suspend fun getBioArticleByArtistId(artistId: Long): ArtistArticle =
        remoteDataSource.getBioArticleByArtistId(artistId).toBioArtistArticle()
}