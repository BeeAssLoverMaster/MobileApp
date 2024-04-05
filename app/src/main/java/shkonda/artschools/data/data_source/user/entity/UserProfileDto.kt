package shkonda.artschools.data.data_source.user.entity

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("username") val userName: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("profilePictureUrl") val profilePictureUrl: String,
    @SerializedName("biography") val biography: String
)
