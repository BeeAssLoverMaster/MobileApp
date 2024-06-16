package shkonda.artschools.domain.usecase.schools

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shkonda.artschools.core.common.Response
import shkonda.artschools.data.repository.schools.SchoolRepository
import shkonda.artschools.domain.model.schools.Schools
import shkonda.artschools.domain.utils.Messages
import java.io.IOException
import javax.inject.Inject

class GetSchoolUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository
) {
    suspend operator fun invoke() : Flow<Response<Schools>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = schoolRepository.getAllSchools()))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetSchoolUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetSchoolUseCase.kt", e.stackTraceToString())
        }
    }
}