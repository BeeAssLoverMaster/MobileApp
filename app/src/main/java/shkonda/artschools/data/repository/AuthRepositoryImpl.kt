package shkonda.artschools.data.repository

import shkonda.artschools.data.data_source.auth.AuthRemoteDataSource
import shkonda.artschools.data.mappers.toAuthResponse
import shkonda.artschools.data.mappers.toLoginDto
import shkonda.artschools.data.mappers.toLoginResponse
import shkonda.artschools.data.mappers.toUserDto
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.domain.model.auth.User
import shkonda.artschools.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun createUser(user: User) = remoteDataSource.createUser(userDto = user.toUserDto()).toAuthResponse()

    override suspend fun signIn(login: Login): LoginResponse = remoteDataSource.signIn(loginDto = login.toLoginDto()).toLoginResponse()
}