package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.schools.entity.SchoolsDto
import shkonda.artschools.domain.model.schools.Program
import shkonda.artschools.domain.model.schools.School
import shkonda.artschools.domain.model.schools.Schools

fun SchoolsDto.toSchools(): Schools {
    return Schools(
        schoolList = this.schoolList.map { dto ->
            School(
                id = dto.id,
                schoolName = dto.schoolName,
                artCategoryId = dto.artCategoryId,
                artCategoryName = dto.artCategoryName,
                description = dto.description,
                city = dto.city,
                street = dto.street,
                schoolImageName = dto.schoolImageName,
                programList = dto.programList.map { programDto ->
                    Program(
                        programId = programDto.programId,
                        programName = programDto.programName
                    )
                }
            )
        }
    )
}
