package shkonda.artschools.domain.usecase.genre

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.genre.Genres
import shkonda.artschools.domain.repository.GenresRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke(categoryId: String) : Flow<Response<Genres>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = genresRepository.getGenres(categoryId)))
            println(genresRepository.getGenres(categoryId))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetGenresUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetGenresUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetGenresUseCase.kt", e.stackTraceToString())
        }
    }
}