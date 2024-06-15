package shkonda.artschools.data.data_source.auth

import shkonda.artschools.data.data_source.auth.entity.AuthResponseDto
import shkonda.artschools.data.data_source.auth.entity.LoginDto
import shkonda.artschools.data.data_source.auth.entity.LoginResponseDto
import shkonda.artschools.data.data_source.auth.entity.UserDto

// Интерфейс AuthRemoteDataSource определяет методы для взаимодействия с удаленным источником данных
interface AuthRemoteDataSource {
    // Принимает объект UserDto и возвращает результат в виде AuthResponseDto
    suspend fun createUser(userDto: UserDto): AuthResponseDto

    // Принимает объект LoginDto и возвращает результат в виде LoginResponseDto
    suspend fun signIn(loginDto: LoginDto): LoginResponseDto
}


