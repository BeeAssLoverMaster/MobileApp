package shkonda.artschools.domain.usecase.articles

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.data.repository.article.ArticleRepository
import shkonda.artschools.domain.model.article.Articles
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(genreId: Long): Flow<Response<Articles>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = articleRepository.getAllArticleByGenreId(genreId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetArticleUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetArticleUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetArticleUseCase.kt", e.stackTraceToString())
        }
    }
}