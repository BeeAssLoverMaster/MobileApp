package shkonda.artschools.data.repository.arts

import shkonda.artschools.data.data_source.arts.genre.ArtGenresRemoteDataSource
import shkonda.artschools.data.mappers.toArtGenres
import shkonda.artschools.domain.model.arts.ArtGenres
import shkonda.artschools.domain.model.arts.ArtTypes
import shkonda.artschools.domain.repository.arts.ArtGenresRepository
import javax.inject.Inject

class ArtGenresRepositoryImpl @Inject constructor(private val remoteDataSource: ArtGenresRemoteDataSource): ArtGenresRepository {
    override suspend fun getArtGenresByArtTypeId(typeId: Long): ArtGenres =
        remoteDataSource.getArtGenresByArtTypeId(typeId).toArtGenres()

}