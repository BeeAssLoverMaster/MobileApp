package shkonda.artschools.domain.usecase.arts

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.arts.ArtCategories
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.data.repository.art.ArtCategoryRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetArtCategoryUseCase @Inject constructor(
    private val artCategoryRepository: ArtCategoryRepository
) {
    suspend operator fun invoke(username: String) : Flow<Response<ArtCategory>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = artCategoryRepository.getArtCategoryByUsername(username)))

        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetArtCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetArtCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        }
    }

    suspend operator fun invoke() : Flow<Response<ArtCategories>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = artCategoryRepository.getAllCategories()))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetArtCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetArtCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        }
    }
}