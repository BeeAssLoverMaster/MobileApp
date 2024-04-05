package shkonda.artschools.data.data_source.auth.entity

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("message")
    val message: String? = null //Ответ сервера
)