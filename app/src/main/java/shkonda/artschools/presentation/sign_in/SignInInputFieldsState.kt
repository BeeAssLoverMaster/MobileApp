package shkonda.artschools.presentation.sign_in

sealed interface SignInInputFieldState {
    data class Error(val errorMessage: String) : SignInInputFieldState
    object Nothing : SignInInputFieldState
}