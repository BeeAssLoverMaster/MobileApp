package shkonda.artschools.domain.model.user

import com.google.gson.annotations.SerializedName

class UserProfile(
    val username: String,
    val bio: String,
    val profileImage: String,
    val artCategory: Long,
    val points: Int,
    val correctAnswers: Int
)