package shkonda.artschools.domain.usecase.user

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.user.UpdateProfileBody
import shkonda.artschools.domain.repository.UserRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(token: String, updateProfileBody: UpdateProfileBody): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.updateProfile(token, updateProfileBody)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("UpdateProfileUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("UpdateProfileUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("UpdateProfileUseCase.kt", e.stackTraceToString())
        }
    }
}