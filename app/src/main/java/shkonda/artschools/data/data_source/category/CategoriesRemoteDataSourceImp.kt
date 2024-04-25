package shkonda.artschools.data.data_source.category

import shkonda.artschools.data.data_source.category.api.CategoryApi
import shkonda.artschools.data.data_source.category.entity.CategoriesDto
import shkonda.artschools.data.data_source.category.entity.CategoryDto
import shkonda.artschools.data.data_source.genre.entity.GenreDto
import javax.inject.Inject

class CategoriesRemoteDataSourceImp @Inject constructor(private val api: CategoryApi) : CategoriesRemoteDataSource {

    override suspend fun getCategories(): List<CategoryDto> = api.getCategories()

    override suspend fun getGenresByCategory(categoryId: String): List<GenreDto> = api.getGenresForCategory(categoryId)

}