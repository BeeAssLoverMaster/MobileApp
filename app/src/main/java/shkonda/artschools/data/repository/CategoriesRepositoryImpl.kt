package shkonda.artschools.data.repository

import shkonda.artschools.data.data_source.category.CategoriesRemoteDataSource
import shkonda.artschools.data.mappers.toCategories
import shkonda.artschools.data.mappers.toGenres
import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.genre.Genres
import shkonda.artschools.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val remoteDataSource: CategoriesRemoteDataSource) : CategoriesRepository {

    override suspend fun getCategories(): Categories =
        remoteDataSource.getCategories().toCategories()

    override suspend fun getGenresByCategory(categoryId: String): Genres =
        remoteDataSource.getGenresByCategory(categoryId).toGenres()

}