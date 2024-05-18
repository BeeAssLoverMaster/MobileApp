package shkonda.artschools.data.data_source.arts.type

import shkonda.artschools.data.data_source.arts.type.api.ArtTypesApi
import shkonda.artschools.data.data_source.arts.type.entity.ArtTypesDto
import javax.inject.Inject

class ArtTypesRemoteDataSourceImpl @Inject constructor(private val api: ArtTypesApi) : ArtTypesRemoteDataSource {
    override suspend fun getArtTypesByArtCategoryId(categoryId: Long): ArtTypesDto =
        api.getTypesByCategoryId(categoryId)
}