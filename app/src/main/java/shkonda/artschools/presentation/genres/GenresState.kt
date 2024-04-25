package shkonda.artschools.presentation.genres

import shkonda.artschools.domain.model.genre.Genres

sealed interface GenresState {
    object Loading : GenresState

    data class Success(val data: Genres) : GenresState
    data class Error(val errorMessage: String) : GenresState
}