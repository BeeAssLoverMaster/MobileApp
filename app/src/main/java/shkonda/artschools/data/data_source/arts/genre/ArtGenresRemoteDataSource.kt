package shkonda.artschools.data.data_source.arts.genre

import shkonda.artschools.data.data_source.arts.genre.entity.ArtGenresDto

interface ArtGenresRemoteDataSource {
    suspend fun getArtGenresByArtTypeId(typeId: Long): ArtGenresDto
}