package shkonda.artschools.domain.model.user

data class UpdateProfileBody(
    val firstName: String,
    val lastName: String,
    val biography: String
)
