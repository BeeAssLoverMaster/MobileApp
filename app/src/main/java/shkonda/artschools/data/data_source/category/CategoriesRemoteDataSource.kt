package shkonda.artschools.data.data_source.category

import shkonda.artschools.data.data_source.category.entity.CategoriesDto
import shkonda.artschools.data.data_source.category.entity.CategoryDto
import shkonda.artschools.data.data_source.genre.entity.GenreDto


interface CategoriesRemoteDataSource {

    suspend fun getCategories() : List<CategoryDto>

    suspend fun getGenresByCategory(categoryId: String) : List<GenreDto>
}