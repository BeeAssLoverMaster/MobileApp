package shkonda.artschools.data.data_source.schools

import shkonda.artschools.data.data_source.schools.api.SchoolApi
import shkonda.artschools.data.data_source.schools.entity.SchoolsDto
import javax.inject.Inject

interface SchoolRemoteDataSource {
    suspend fun getAllSchools(): SchoolsDto
}