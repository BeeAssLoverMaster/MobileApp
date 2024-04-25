package shkonda.artschools.data.data_source.genre.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.genre.entity.GenreDto

interface GenreApi {
    @GET("api/genres")
    suspend fun getGenres(@Query("category") categoryId: String): List<GenreDto>

}