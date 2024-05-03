package shkonda.artschools.domain.usecase.quizzes

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.quizzes.Quizzes
import shkonda.artschools.domain.repository.quizzes.QuizzesRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val quizzesRepository: QuizzesRepository
) {
    suspend operator fun invoke(genreId: Long): Flow<Response<Quizzes>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = quizzesRepository.getQuizzesByArtGenreId(genreId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetQuizUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetQuizUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetQuizUseCase.kt", e.stackTraceToString())
        }
    }
}