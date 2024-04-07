package shkonda.artschools.domain.model.user

class UserProfile(
//    val username: String,
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val score: Int,
    val profilePictureUrl: String,
    val biography: String?
)