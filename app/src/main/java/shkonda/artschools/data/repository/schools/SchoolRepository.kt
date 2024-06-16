package shkonda.artschools.data.repository.schools

import shkonda.artschools.domain.model.schools.Schools

interface SchoolRepository {
    suspend fun getAllSchools(): Schools
}