package shkonda.artschools.data.data_source.user.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import shkonda.artschools.data.data_source.user.entity.UpdateProfileBodyDto
import shkonda.artschools.data.data_source.user.entity.UserProfileDto

interface UserApi {

    @GET("api/users/get_profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileDto

    /*  @PUT("api/Users/UpdatePassword")
      suspend fun updatePassword(
          @Header("Authorization") token: String,
          @Body updatePasswordBodyDto: UpdatePasswordBodyDto
      )*/

    @PUT("api/Users/UpdateProfile")
     suspend fun updateProfile(
         @Header("Authorization") token: String,
         @Body updateProfileBodyDto: UpdateProfileBodyDto
     )

    @Multipart
    @POST("api/users/add_img")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    )

    @DELETE("api/Users/DeleteUser")
    suspend fun deleteAccount(
        @Query("userId") userId: String
    )

    /*@GET("api/Users/GetLeaderboard")
    suspend fun getLeaderboard(): ArrayList<LeaderboardDto>*/
}