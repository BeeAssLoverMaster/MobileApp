package shkonda.artschools.domain.usecase.articles

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.data.repository.article.ArticleRepository
import shkonda.artschools.domain.model.article.ArtistArticle
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetBioArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(artistId: Long): Flow<Response<ArtistArticle>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = articleRepository.getBioArticleByArtistId(artistId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetBioArticleUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetBioArticleUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetBioArticleUseCase.kt", e.stackTraceToString())
        }
    }
}