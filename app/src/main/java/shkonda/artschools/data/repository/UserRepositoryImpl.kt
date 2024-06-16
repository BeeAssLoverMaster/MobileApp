package shkonda.artschools.data.repository

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.UserRemoteDataSource
import shkonda.artschools.data.mappers.toUpdateProfileBodyDto
import shkonda.artschools.data.mappers.toUserProfile
import shkonda.artschools.domain.model.user.UpdateProfileBody
import shkonda.artschools.domain.model.user.UserProfile
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override suspend fun getUserProfile(token: String): UserProfile =
        remoteDataSource.getUserProfile(token).toUserProfile()

    override suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody) =
        remoteDataSource.updateProfile(
            token = token,
            updateProfileBodyDto = updateProfileBody.toUpdateProfileBodyDto()
        )

    override suspend fun addPoints(token: String, points: Int, correctAnswers: Int, quizId: Long) =
        remoteDataSource.addPointsToUser(token, points, correctAnswers, quizId)

    override suspend fun updateArtCategory(token: String, artCategoryId: Long) =
        remoteDataSource.updateArtCategory(
            token = token,
            artCategoryId = artCategoryId
        )

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        remoteDataSource.uploadProfilePicture(token = token, file = file)
}