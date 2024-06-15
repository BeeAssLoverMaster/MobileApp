package shkonda.artschools.presentation.main_page.art_genres.states

import shkonda.artschools.domain.model.arts.ArtGenres

sealed interface AllArtGenresState {
    object Loading: AllArtGenresState
    data class Success(val genresData: ArtGenres) : AllArtGenresState
    data class Error(val errorMessage: String) : AllArtGenresState
}