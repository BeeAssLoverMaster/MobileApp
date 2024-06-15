package shkonda.artschools.data.repository

import shkonda.artschools.domain.model.auth.AuthResponse
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.domain.model.auth.User

// Интерфейс AuthRepository определяет методы для аутентификации пользователей
interface AuthRepository {
    // Метод для создания нового пользователя
    // Принимает объект User и возвращает результат в виде AuthResponse
    suspend fun createUser(user: User): AuthResponse

    // Метод для входа пользователя в систему
    // Принимает объект Login и возвращает результат в виде LoginResponse
    suspend fun signIn(login: Login): LoginResponse
}

