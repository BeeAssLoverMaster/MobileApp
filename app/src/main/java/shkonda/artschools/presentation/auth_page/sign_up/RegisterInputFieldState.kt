package shkonda.artschools.presentation.auth_page.sign_up

sealed interface RegisterInputFieldState {
    data class Error(val errorMessage: String) : RegisterInputFieldState
    object Nothing : RegisterInputFieldState
}