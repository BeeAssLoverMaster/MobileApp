package shkonda.artschools.data.repository.art

import shkonda.artschools.domain.model.arts.ArtGenres

interface ArtGenresRepository {
    suspend fun getArtGenresByArtTypeId(typeId: Long): ArtGenres
}