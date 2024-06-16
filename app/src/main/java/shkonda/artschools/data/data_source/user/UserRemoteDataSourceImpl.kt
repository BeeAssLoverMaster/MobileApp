package shkonda.artschools.data.data_source.user

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.api.UserApi
import shkonda.artschools.data.data_source.user.entity.UpdateProfileBodyDto
import shkonda.artschools.data.data_source.user.entity.UserProfileDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val api: UserApi) : UserRemoteDataSource {

    override suspend fun getUserProfile(token: String): UserProfileDto = api.getUserProfile(token)
    override suspend fun addPointsToUser(token: String, points: Int,correctAnswers: Int, quizId: Long) =
        api.addPointsToUser(token, points, correctAnswers, quizId)

    override suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto) =
        api.updateProfile(token = token, updateProfileBodyDto = updateProfileBodyDto)

    override suspend fun updateArtCategory(token: String, artCategoryId: Long) =
        api.updateArtCategory(token = token, artCategoryId = artCategoryId)

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        api.uploadProfilePicture(token = token, file = file)
}