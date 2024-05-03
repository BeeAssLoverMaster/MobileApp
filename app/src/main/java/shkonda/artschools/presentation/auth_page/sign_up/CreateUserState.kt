package shkonda.artschools.presentation.auth_page.sign_up

sealed interface CreateUserState {
    object Success : CreateUserState
    data class Error(val errorMessage: String) : CreateUserState
    object Loading : CreateUserState
    object Nothing : CreateUserState
}