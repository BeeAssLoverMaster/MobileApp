package shkonda.artschools.data.data_source.auth.api

import retrofit2.http.Body
import retrofit2.http.POST
import shkonda.artschools.data.data_source.auth.entity.AuthResponseDto
import shkonda.artschools.data.data_source.auth.entity.LoginDto
import shkonda.artschools.data.data_source.auth.entity.LoginResponseDto
import shkonda.artschools.data.data_source.auth.entity.UserDto

interface AuthApi {
    @POST("/api/sign-up")
    suspend fun createUserAA(@Body userDto: UserDto): AuthResponseDto

    @POST("/api/sign-in")
    suspend fun signIn(@Body loginDto: LoginDto): LoginResponseDto

}