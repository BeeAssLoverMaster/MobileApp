package shkonda.artschools.presentation.main_page.art_genres

import shkonda.artschools.domain.model.arts.ArtGenres

sealed interface ArtGenresState {
    object Loading : ArtGenresState
    data class Success(val genresData: ArtGenres) : ArtGenresState
    data class Error(val errorMessage: String) : ArtGenresState
}