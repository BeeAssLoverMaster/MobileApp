package shkonda.artschools.domain.usecase.arts

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.arts.ArtTypes
import shkonda.artschools.data.repository.art.ArtTypesRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetArtTypesUseCase @Inject constructor(
    private val artTypesRepository: ArtTypesRepository
) {
    suspend operator fun invoke(categoryId: Long): Flow<Response<ArtTypes>> = flow {
        try {
            emit(Response.Loading)
            val temp = Response.Success(data = artTypesRepository.getArtTypesByArtCategoryId(categoryId))
            println("temp:\n$temp")

            emit(Response.Success(data = artTypesRepository.getArtTypesByArtCategoryId(categoryId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetArtTypesUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetArtTypesUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetArtTypesUseCase.kt", e.stackTraceToString())
        }
    }
}
