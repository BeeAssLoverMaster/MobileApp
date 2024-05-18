package shkonda.artschools.domain.usecase.user

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.data.repository.UserRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class AddPointsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(token: String, points: Int, quizId: Long): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.addPoints(token, points, quizId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("AddPointsUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Navigator.navigate(NavScreen.SignInScreen.route) {
                    popUpTo(0)
                }
            } else {
                val errorMessage = e.getErrorMessage()
                if (errorMessage != null) {
                    emit(Response.Error(errorMessage = errorMessage))
                } else {
                    emit(Response.Error(errorMessage = Messages.UNKNOWN))
                }
                Log.e("AddPointsUseCase.kt", e.stackTraceToString())
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("AddPointsUseCase.kt", e.stackTraceToString())
        }
    }
}