package shkonda.artschools.domain.usecase.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.model.auth.LoginResponse
import shkonda.artschools.data.repository.AuthRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(login: Login): Flow<Response<LoginResponse>> = flow {
        try {
            val response = authRepository.signIn(login = login)
            Log.d("SignInUseCase", "LoginResponse: $response")
            emit(Response.Loading)
            emit(Response.Success(data = authRepository.signIn(login = login)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("SignInUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("SignInUseCase.kt", e.stackTraceToString())
            Log.e("SignInUseCase.kt", "$login")
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("SignInUseCase.kt", e.stackTraceToString())
            Log.e("SignInUseCase.kt", "$login")
        }
    }
}