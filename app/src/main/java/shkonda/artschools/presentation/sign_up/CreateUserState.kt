package shkonda.artschools.presentation.sign_up

sealed interface CreateUserState {
    object Success : CreateUserState
    data class Error(val errorMessage: String) : CreateUserState
    object Loading : CreateUserState
    object Nothing : CreateUserState
}