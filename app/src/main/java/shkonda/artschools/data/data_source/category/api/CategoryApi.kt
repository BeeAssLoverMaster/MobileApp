package shkonda.artschools.data.data_source.category.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.category.entity.CategoryDto
import shkonda.artschools.data.data_source.genre.entity.GenreDto

interface CategoryApi {
    @GET("api/categories")
    suspend fun getCategories() : List<CategoryDto>

    @GET("api/genres")
    suspend fun getGenresForCategory(@Query("category") category: String): List<GenreDto>
}