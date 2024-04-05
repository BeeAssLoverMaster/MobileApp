package shkonda.artschools.domain.model.auth

data class LoginResponse(
//    val token: LoginResponseBody
    val token: String
)

data class LoginResponseBody(
    val accessToken: String
)
