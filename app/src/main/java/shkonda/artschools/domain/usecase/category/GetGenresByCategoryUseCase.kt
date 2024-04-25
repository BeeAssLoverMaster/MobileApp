package shkonda.artschools.domain.usecase.category

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getErrorMessage
import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.genre.Genre
import shkonda.artschools.domain.model.genre.Genres
import shkonda.artschools.domain.repository.CategoriesRepository
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetGenresByCategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    suspend operator fun invoke(categoryId: String): Flow<Response<Genres>> = flow {
        try {
            emit(Response.Loading)

            val genres = categoriesRepository.getGenresByCategory(categoryId)
            emit(Response.Success(data = genres))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetGenresByCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetGenresByCategoryUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetGenresByCategoryUseCase.kt", e.stackTraceToString())
        }
    }
}


/*
class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    suspend operator fun invoke() : Flow<Response<Categories>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = categoriesRepository.getCategories()))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        }
    }
}*/
