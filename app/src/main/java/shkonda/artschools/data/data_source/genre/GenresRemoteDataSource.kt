package shkonda.artschools.data.data_source.genre

import shkonda.artschools.data.data_source.genre.entity.GenreDto

interface GenresRemoteDataSource {
    suspend fun getGenres(categoryId: String) : List<GenreDto>
}