package shkonda.artschools.data.repository

import okhttp3.MultipartBody
import shkonda.artschools.domain.model.user.UpdateProfileBody
import shkonda.artschools.domain.model.user.UserProfile

interface UserRepository {
    suspend fun getUserProfile(token: String): UserProfile

    suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody)
    suspend fun addPoints(token: String, points: Int, quizId: Long)

    suspend fun updateArtCategory(token: String, artCategoryId: Long)

    suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part)
}