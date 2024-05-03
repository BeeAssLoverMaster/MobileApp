//package shkonda.artschools.presentation.themes.ThemeStates
//
//import shkonda.artschools.domain.model.theme.Artists
//
//sealed interface ArtistState {
//    object Loading : ArtistState
//
//    data class Success(val data: Artists) : ArtistState
//    data class Error(val errorMessage: String) : ArtistState
//}