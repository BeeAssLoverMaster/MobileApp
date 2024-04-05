package shkonda.artschools.data.repository

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.UserRemoteDataSource
import shkonda.artschools.data.mappers.toUserProfile
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UserRemoteDataSource) : UserRepository {

    override suspend fun getUserProfile(token: String): UserProfile =
        remoteDataSource.getUserProfile(token).toUserProfile()

    /* override suspend fun updatePassword(token: String, updatePasswordBody: UpdatePasswordBody) =
         remoteDataSource.updatePassword(
             token = token,
             updatePasswordBodyDto = updatePasswordBody.toUpdatePasswordBodyDto()
         )*/

    /*override suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody) =
        remoteDataSource.updateProfile(
            token = token,
            updateProfileBodyDto = updateProfileBody.toUpdateProfileBodyDto()
        )*/

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        remoteDataSource.uploadProfilePicture(token = token, file = file)

    override suspend fun deleteAccount(userId: String) =
        remoteDataSource.deleteAccount(userId = userId)

    /*override suspend fun getLeaderboard(): ArrayList<Leaderboard> =
        remoteDataSource.getLeaderboard().toLeaderboard()*/
}