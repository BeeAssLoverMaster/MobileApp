package shkonda.artschools.data.data_source.arts.category.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.arts.category.entity.ArtCategoryDto

interface ArtCategoryApi {
    @GET("api/categories/get")
    suspend fun getCategoryByUsername(@Query("username") username: String) : ArtCategoryDto

}