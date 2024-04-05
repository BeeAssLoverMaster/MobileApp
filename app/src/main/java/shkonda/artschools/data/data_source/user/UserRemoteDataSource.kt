package shkonda.artschools.data.data_source.user

import okhttp3.MultipartBody
import shkonda.artschools.data.data_source.user.entity.UserProfileDto

interface UserRemoteDataSource {

    suspend fun getUserProfile(token: String): UserProfileDto

//    suspend fun updatePassword(token: String, updatePasswordBodyDto: UpdatePasswordBodyDto)

//    suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto)

    suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part)

    suspend fun deleteAccount(userId: String)

//    suspend fun getLeaderboard(): ArrayList<LeaderboardDto>
}