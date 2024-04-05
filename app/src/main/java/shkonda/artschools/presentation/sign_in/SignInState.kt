package shkonda.artschools.presentation.sign_in

import shkonda.artschools.domain.model.auth.LoginResponse

sealed interface SignInState {
    data class Success(val data: LoginResponse) : SignInState
    data class Error(val errorMessage: String) : SignInState
    object Loading : SignInState
    object Nothing : SignInState
}