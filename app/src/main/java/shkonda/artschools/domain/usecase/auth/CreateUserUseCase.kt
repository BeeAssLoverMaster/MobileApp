package shkonda.artschools.domain.usecase.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.auth.User
import shkonda.artschools.domain.repository.AuthRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(user: User): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            emit(
                Response.Success(
                    data = authRepository.createUser(user = user).message ?: Messages.USER_CRE_SUC
                )
            )
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
            Log.e("CreateUserUseCase.kt", "$user")

        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
        }
    }
    // TODO: Переписать  выводы от сервера
}