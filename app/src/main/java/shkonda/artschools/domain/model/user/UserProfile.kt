package shkonda.artschools.domain.model.user

import com.google.gson.annotations.SerializedName

class UserProfile(
    val username: String,
    val bio: String,
    val profileImage: String,
    val artCategory: Int,
    val points: Int
)