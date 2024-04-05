package shkonda.artschools.domain.repository

import okhttp3.MultipartBody
import shkonda.artschools.domain.model.user.UserProfile

interface UserRepository {
    suspend fun getUserProfile(token: String): UserProfile
//    suspend fun updatePassword(token: String, updatePasswordBody: UpdatePasswordBody)

//    suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody)

    suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part)

    suspend fun deleteAccount(userId: String)

//    suspend fun getLeaderboard(): ArrayList<Leaderboard>
}