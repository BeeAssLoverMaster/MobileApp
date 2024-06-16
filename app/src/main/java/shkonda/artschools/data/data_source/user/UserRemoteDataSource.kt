package shkonda.artschools.data.data_source.user

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.entity.UpdateProfileBodyDto
import shkonda.artschools.data.data_source.user.entity.UserProfileDto

interface UserRemoteDataSource {

    suspend fun getUserProfile(token: String): UserProfileDto
    suspend fun addPointsToUser(token: String, points: Int, correctAnswers: Int, quizId: Long)
    suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto)
    suspend fun updateArtCategory(token: String, artCategoryId: Long)

    suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part)
}