package shkonda.artschools.data.data_source.articles.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.articles.entity.ArticlesDto
import shkonda.artschools.data.data_source.articles.entity.ArtistArticleDto

interface ArticleApi {
    @GET("api/article/get_by_genre")
    suspend fun getAllArticleByGenreId(@Query("genreId") genreId: Long): ArticlesDto

    @GET("api/artist/get_by_id/article")
    suspend fun getBioArticleByArtistId(@Query("artistId") artistId: Long): ArtistArticleDto
}