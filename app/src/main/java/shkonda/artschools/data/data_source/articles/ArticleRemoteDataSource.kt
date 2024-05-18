package shkonda.artschools.data.data_source.articles

import shkonda.artschools.data.data_source.articles.entity.ArticlesDto
import shkonda.artschools.data.data_source.articles.entity.ArtistArticleDto

interface ArticleRemoteDataSource {
    suspend fun getAllArticleByGenreId(genreId: Long): ArticlesDto
    suspend fun getBioArticleByArtistId(artistId: Long): ArtistArticleDto
}