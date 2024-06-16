package shkonda.artschools.data.data_source.schools

import shkonda.artschools.data.data_source.schools.api.SchoolApi
import shkonda.artschools.data.data_source.schools.entity.SchoolsDto
import javax.inject.Inject

class SchoolRemoteDataSourceImpl @Inject constructor(private val api: SchoolApi) : SchoolRemoteDataSource {
    override suspend fun getAllSchools(): SchoolsDto = api.getAllSchools()
}