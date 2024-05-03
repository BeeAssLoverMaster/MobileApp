package shkonda.artschools.presentation.main_page.home.states

import shkonda.artschools.domain.model.arts.ArtTypes

sealed interface ArtTypesState {
    object Loading : ArtTypesState
    data class Success(val typesData: ArtTypes) : ArtTypesState
    data class Error(val errorMessage: String) : ArtTypesState
}