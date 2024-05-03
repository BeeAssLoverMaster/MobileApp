package shkonda.artschools.data.data_source.auth

import shkonda.artschools.data.data_source.auth.api.AuthApi
import shkonda.artschools.data.data_source.auth.entity.LoginDto
import shkonda.artschools.data.data_source.auth.entity.LoginResponseDto
import shkonda.artschools.data.data_source.auth.entity.UserDto
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val api: AuthApi) :
    AuthRemoteDataSource {
    override suspend fun createUser(userDto: UserDto) = api.createUser(userDto = userDto)

    override suspend fun signIn(loginDto: LoginDto): LoginResponseDto = api.signIn(loginDto = loginDto)
}