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
import retrofit2.http.Path
import retrofit2.http.Query
import shkonda.artschools.data.data_source.user.entity.UpdateProfileBodyDto
import shkonda.artschools.data.data_source.user.entity.UserProfileDto
import shkonda.artschools.domain.model.arts.ArtCategory

interface UserApi {

    @GET("api/users/get_profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileDto
    @PUT("api/Users/UpdateProfile")
     suspend fun updateProfile(
         @Header("Authorization") token: String,
         @Body updateProfileBodyDto: UpdateProfileBodyDto
     )

     @PUT("api/users/add_points/{points}/{correctAnswers}/{quizId}")
     suspend fun addPointsToUser(
         @Header("Authorization") token: String,
         @Path("points") points: Int,
         @Path("correctAnswers") correctAnswers: Int,
         @Path("quizId") quizId: Long
     )

     @PUT("api/users/update_art_category/{artCategoryId}")
     suspend fun updateArtCategory(
         @Header("Authorization") token: String,
         @Path("artCategoryId") artCategoryId: Long
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
}