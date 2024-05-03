package shkonda.artschools.data.data_source.arts.genre

import shkonda.artschools.data.data_source.arts.genre.api.ArtGenresApi
import shkonda.artschools.data.data_source.arts.genre.entity.ArtGenresDto
import javax.inject.Inject

class ArtGenresRemoteDataSourceImpl @Inject constructor(private val api: ArtGenresApi) : ArtGenresRemoteDataSource {
    override suspend fun getArtGenresByArtTypeId(typeId: Long): ArtGenresDto =
        api.getGenresByArtTypeId(typeId)
}