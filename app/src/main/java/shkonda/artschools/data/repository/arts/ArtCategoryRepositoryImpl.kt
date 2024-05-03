package shkonda.artschools.data.repository.arts

import shkonda.artschools.data.data_source.arts.category.ArtCategoryRemoteDataSource
import shkonda.artschools.data.mappers.toArtCategory
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.repository.arts.ArtCategoryRepository
import javax.inject.Inject

class ArtCategoryRepositoryImpl @Inject constructor(private val remoteDataSource: ArtCategoryRemoteDataSource) :
    ArtCategoryRepository {

    override suspend fun getArtCategoryByUsername(username: String): ArtCategory =
        remoteDataSource.getArtCategoryByUsername(username).toArtCategory()

}