package shkonda.artschools.data.repository.art

import shkonda.artschools.data.data_source.arts.category.ArtCategoryRemoteDataSource
import shkonda.artschools.data.mappers.toArtCategories
import shkonda.artschools.data.mappers.toArtCategory
import shkonda.artschools.domain.model.arts.ArtCategories
import shkonda.artschools.domain.model.arts.ArtCategory
import javax.inject.Inject

class ArtCategoryRepositoryImpl @Inject constructor(private val remoteDataSource: ArtCategoryRemoteDataSource) :
    ArtCategoryRepository {

    override suspend fun getArtCategoryByUsername(username: String): ArtCategory =
        remoteDataSource.getArtCategoryByUsername(username).toArtCategory()

    override suspend fun getAllCategories(): ArtCategories =
        remoteDataSource.getAllCategories().toArtCategories()
}