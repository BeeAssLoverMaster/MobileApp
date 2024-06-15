package shkonda.artschools.data.repository

import shkonda.artschools.data.data_source.auth.AuthRemoteDataSource
import shkonda.artschools.data.mappers.toAuthResponse
import shkonda.artschools.data.mappers.toLoginDto
import shkonda.artschools.data.mappers.toLoginResponse
import shkonda.artschools.data.mappers.toUserDto
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.domain.model.auth.User
import javax.inject.Inject

// Класс для реализации интерфейса, используемого для авторизации
class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) : AuthRepository {

    // Метод для создания пользователя, который использует удаленный источник данных
    // Конвертирует объект user в userDto и отправляет запрос на создание пользователя
    // Возвращает результат в виде AuthResponse.
    override suspend fun createUser(user: User) = remoteDataSource.createUser(userDto = user.toUserDto()).toAuthResponse()

    // Метод для входа в систему, который использует удаленный источник данных
    // Конвертирует объект login в loginDto и отправляет запрос на вход
    // Возвращает результат в виде LoginResponse
    override suspend fun signIn(login: Login): LoginResponse = remoteDataSource.signIn(loginDto = login.toLoginDto()).toLoginResponse()
}

