package shkonda.artschools.data.data_source.auth.entity

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("token")
    val token: String
)

data class LoginResponseBodyDto(
    @SerializedName("accessToken")
    val accessToken: String
)
