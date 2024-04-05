package shkonda.artschools.presentation.sign_up

sealed interface RegisterInputFieldState {
    data class Error(val errorMessage: String) : RegisterInputFieldState
    object Nothing : RegisterInputFieldState
}