package shkonda.artschools.data.data_source.user

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.api.UserApi
import shkonda.artschools.data.data_source.user.entity.UserProfileDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val api: UserApi) : UserRemoteDataSource {

    override suspend fun getUserProfile(token: String): UserProfileDto = api.getUserProfile(token)

    /*override suspend fun updatePassword(
        token: String,
        updatePasswordBodyDto: UpdatePasswordBodyDto
    ) = api.updatePassword(token = token, updatePasswordBodyDto = updatePasswordBodyDto)*/

    /*override suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto) =
        api.updateProfile(token = token, updateProfileBodyDto = updateProfileBodyDto)*/

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        api.uploadProfilePicture(token = token, file = file)

    override suspend fun deleteAccount(userId: String) = api.deleteAccount(userId = userId)

    /* override suspend fun getLeaderboard(): ArrayList<LeaderboardDto> =
         api.getLeaderboard()*/
}