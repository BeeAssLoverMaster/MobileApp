package shkonda.artschools.data.data_source.arts.category

import shkonda.artschools.data.data_source.arts.category.entity.ArtCategoryDto


interface ArtCategoryRemoteDataSource {

    suspend fun getArtCategoryByUsername(username: String) : ArtCategoryDto

}