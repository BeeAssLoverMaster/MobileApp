package shkonda.artschools.domain.repository.arts

import shkonda.artschools.domain.model.arts.ArtGenres

interface ArtGenresRepository {
    suspend fun getArtGenresByArtTypeId(typeId: Long): ArtGenres
}