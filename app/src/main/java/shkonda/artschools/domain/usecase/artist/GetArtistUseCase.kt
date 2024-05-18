package shkonda.artschools.domain.usecase.artist

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.data.repository.artist.ArtistRepository
import shkonda.artschools.domain.model.artist.SimpleArtists
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetArtistUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(genreId: Long): Flow<Response<SimpleArtists>> = flow {
        try {
            emit(Response.Loading)
            val response = Response.Success(data = artistRepository.getAllArtistByGenreId(genreId))
            println("response: $response")
            emit(Response.Success(data = artistRepository.getAllArtistByGenreId(genreId)))
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