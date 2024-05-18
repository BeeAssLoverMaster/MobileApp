package shkonda.artschools.data.data_source.arts.type.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.arts.type.entity.ArtTypesDto

interface ArtTypesApi {
    @GET("api/types/get")
    suspend fun getTypesByCategoryId(@Query("categoryId") categoryId: Long): ArtTypesDto
}