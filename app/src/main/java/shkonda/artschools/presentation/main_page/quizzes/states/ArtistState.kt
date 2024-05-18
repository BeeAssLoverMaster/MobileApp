package shkonda.artschools.presentation.main_page.quizzes.states

import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtists

sealed interface ArtistState {
    object Loading : ArtistState
    data class Success(val artistData: Artist) : ArtistState
    data class Error(val errorMessage: String) : ArtistState

}

sealed interface SimpleArtistState {
    object Loading : SimpleArtistState
    data class Success(val simpleArtistData: SimpleArtists) : SimpleArtistState
    data class Error(val errorMessage: String) : SimpleArtistState
}