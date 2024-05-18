package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.auth.entity.AuthResponseDto
import shkonda.artschools.data.data_source.auth.entity.LoginDto
import shkonda.artschools.data.data_source.auth.entity.LoginResponseDto
import shkonda.artschools.data.data_source.auth.entity.UserDto
import shkonda.artschools.domain.model.auth.AuthResponse
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.domain.model.auth.User

fun User.toUserDto(): UserDto {
    return UserDto(
        username = username,
        email = email,
        password = password
    )
}

fun AuthResponseDto.toAuthResponse(): AuthResponse {
    return AuthResponse(
        message = message
    )
}

fun Login.toLoginDto(): LoginDto {
    return LoginDto(
        email = email,
        password = password
    )
}

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        token = token
    )
}