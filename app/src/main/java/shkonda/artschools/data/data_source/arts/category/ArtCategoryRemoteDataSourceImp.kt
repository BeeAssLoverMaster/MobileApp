package shkonda.artschools.data.data_source.arts.category

import shkonda.artschools.data.data_source.arts.category.api.ArtCategoryApi
import shkonda.artschools.data.data_source.arts.category.entity.ArtCategoryDto
import javax.inject.Inject

class ArtCategoryRemoteDataSourceImp @Inject constructor(private val api: ArtCategoryApi) :
    ArtCategoryRemoteDataSource {

    override suspend fun getArtCategoryByUsername(username: String): ArtCategoryDto = api.getCategoryByUsername(username)

}