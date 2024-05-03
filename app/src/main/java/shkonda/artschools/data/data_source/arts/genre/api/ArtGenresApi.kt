package shkonda.artschools.data.data_source.arts.genre.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.arts.genre.entity.ArtGenresDto
import shkonda.artschools.data.data_source.arts.type.entity.ArtTypesDto

interface ArtGenresApi {
    @GET("api/genres/get")
    suspend fun getGenresByArtTypeId(@Query("typeId") typeId: Long): ArtGenresDto
}