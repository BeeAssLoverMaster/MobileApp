package shkonda.artschools.domain.repository

import shkonda.artschools.domain.model.genre.Genres

interface GenresRepository {
    suspend fun getGenres(categoryId: String) : Genres
}