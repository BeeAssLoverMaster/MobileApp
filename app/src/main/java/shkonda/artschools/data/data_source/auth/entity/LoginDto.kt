package shkonda.artschools.data.data_source.auth.entity

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)