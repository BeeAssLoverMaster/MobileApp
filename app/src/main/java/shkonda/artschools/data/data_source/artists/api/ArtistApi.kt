package shkonda.artschools.data.data_source.artists.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.artists.entity.ArtistDto
import shkonda.artschools.data.data_source.artists.entity.SimpleArtistsDto

interface ArtistApi {
    @GET("api/artist/get_artist_by_article")
    suspend fun getArtistByArticleId(@Query("articleId") articleId: Long): ArtistDto

    @GET("api/artist/get_artist_quizzes")
    suspend fun getAllArtistByGenreId(@Query("genreId") genreId: Long): SimpleArtistsDto
}