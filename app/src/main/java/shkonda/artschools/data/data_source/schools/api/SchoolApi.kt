package shkonda.artschools.data.data_source.schools.api

import retrofit2.http.GET
import shkonda.artschools.data.data_source.schools.entity.SchoolsDto

interface SchoolApi {
    @GET("api/school/get/all/schools")
    suspend fun getAllSchools(): SchoolsDto
}