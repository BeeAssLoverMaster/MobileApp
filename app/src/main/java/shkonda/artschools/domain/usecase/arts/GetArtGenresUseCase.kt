package shkonda.artschools.domain.usecase.arts

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.arts.ArtGenres
import shkonda.artschools.data.repository.art.ArtGenresRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetArtGenresUseCase @Inject constructor(
    private val artGenresRepository: ArtGenresRepository
) {
    suspend operator fun invoke(typeId: Long): Flow<Response<ArtGenres>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(data = artGenresRepository.getArtGenresByArtTypeId(typeId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetArtGenresUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetArtGenresUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetArtGenresUseCase.kt", e.stackTraceToString())
        }
    }
}