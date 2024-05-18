package shkonda.artschools.domain.usecase.question

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.data.repository.article.ArticleRepository
import shkonda.artschools.data.repository.question.QuestionRepository
import shkonda.artschools.domain.model.article.Articles
import shkonda.artschools.domain.model.question.QuestionList
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetAllQuestionUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {
    suspend operator fun invoke(quizId: Long): Flow<Response<QuestionList>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = questionRepository.getAllQuestionAndAnswersByQuizId(quizId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetAllQuestionUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetAllQuestionUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetAllQuestionUseCase.kt", e.stackTraceToString())
        }
    }
}