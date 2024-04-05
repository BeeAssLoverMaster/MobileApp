package shkonda.artschools.data.data_source.auth

import shkonda.artschools.data.data_source.auth.entity.AuthResponseDto
import shkonda.artschools.data.data_source.auth.entity.LoginDto
import shkonda.artschools.data.data_source.auth.entity.LoginResponseDto
import shkonda.artschools.data.data_source.auth.entity.UserDto

interface AuthRemoteDataSource {
    suspend fun createUser(userDto: UserDto): AuthResponseDto

    suspend fun signIn(loginDto: LoginDto) : LoginResponseDto
}