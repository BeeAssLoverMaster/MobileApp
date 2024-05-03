package shkonda.artschools.domain.usecase.user

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.repository.UserRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class UploadProfilePictureUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(token: String, filePart: MultipartBody.Part): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            println(filePart)
            emit(Response.Success(data = repository.uploadProfilePicture(token, filePart)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("UploadProfilePictureUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("UploadProfilePictureUseCase.kt", "HttpException:\n" + e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("UploadProfilePictureUseCase.kt", "Exception:\n" + e.stackTraceToString())
        }
    }
}