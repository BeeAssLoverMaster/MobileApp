package shkonda.artschools.data.data_source.auth.entity

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("username") //для соответствия ключу JSON
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)