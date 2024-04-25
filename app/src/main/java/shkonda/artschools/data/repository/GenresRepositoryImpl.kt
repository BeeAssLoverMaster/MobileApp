package shkonda.artschools.data.repository

import shkonda.artschools.data.data_source.category.CategoriesRemoteDataSource
import shkonda.artschools.data.data_source.genre.GenresRemoteDataSource
import shkonda.artschools.data.mappers.toGenres
import shkonda.artschools.domain.model.genre.Genres
import shkonda.artschools.domain.repository.GenresRepository
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(private val remoteDataSource: GenresRemoteDataSource) : GenresRepository {
    override suspend fun getGenres(categoryId: String): Genres =
        remoteDataSource.getGenres(categoryId).toGenres()

}