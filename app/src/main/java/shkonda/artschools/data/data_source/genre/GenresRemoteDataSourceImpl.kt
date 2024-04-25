package shkonda.artschools.data.data_source.genre

import shkonda.artschools.data.data_source.genre.api.GenreApi
import shkonda.artschools.data.data_source.genre.entity.GenreDto
import javax.inject.Inject

class GenresRemoteDataSourceImpl @Inject constructor(private val api: GenreApi) : GenresRemoteDataSource {
    override suspend fun getGenres(categoryId: String): List<GenreDto> =
        api.getGenres(categoryId)
}