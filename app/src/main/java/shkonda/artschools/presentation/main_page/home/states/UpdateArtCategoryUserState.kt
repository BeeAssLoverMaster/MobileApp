package shkonda.artschools.presentation.main_page.home.states

sealed interface UpdateArtCategoryUserState {
    object Nothing : UpdateArtCategoryUserState
    object Loading : UpdateArtCategoryUserState
    object Success : UpdateArtCategoryUserState
    data class Error(val errorMessage: String) : UpdateArtCategoryUserState
}