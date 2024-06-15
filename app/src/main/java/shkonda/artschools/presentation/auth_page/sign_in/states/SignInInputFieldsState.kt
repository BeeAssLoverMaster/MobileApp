package shkonda.artschools.presentation.auth_page.sign_in.states

sealed interface SignInInputFieldState {
    data class Error(val errorMessage: String) : SignInInputFieldState
    object Nothing : SignInInputFieldState
}