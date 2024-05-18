package shkonda.artschools.data.repository.article

import shkonda.artschools.domain.model.article.Articles
import shkonda.artschools.domain.model.article.ArtistArticle

interface ArticleRepository {
    suspend fun getAllArticleByGenreId(genreId: Long): Articles
    suspend fun getBioArticleByArtistId(artistId: Long): ArtistArticle
}