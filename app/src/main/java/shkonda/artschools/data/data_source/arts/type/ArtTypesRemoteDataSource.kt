package shkonda.artschools.data.data_source.arts.type

import shkonda.artschools.data.data_source.arts.type.entity.ArtTypesDto

interface ArtTypesRemoteDataSource {
    suspend fun getArtTypesByArtCategoryId(categoryId: Long): ArtTypesDto
}