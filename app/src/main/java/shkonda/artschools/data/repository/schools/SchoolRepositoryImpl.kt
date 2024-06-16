package shkonda.artschools.data.repository.schools

import shkonda.artschools.data.data_source.schools.SchoolRemoteDataSource
import shkonda.artschools.data.mappers.toSchools
import shkonda.artschools.domain.model.schools.Schools
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(private val remoteDataSource: SchoolRemoteDataSource) : SchoolRepository {
    override suspend fun getAllSchools(): Schools =
        remoteDataSource.getAllSchools().toSchools()

}