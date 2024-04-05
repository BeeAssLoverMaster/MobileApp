package shkonda.artschools.domain.repository

import shkonda.artschools.domain.model.auth.AuthResponse
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.domain.model.auth.User

interface AuthRepository {
    suspend fun createUser(user: User) : AuthResponse

    suspend fun signIn(login: Login) : LoginResponse
}