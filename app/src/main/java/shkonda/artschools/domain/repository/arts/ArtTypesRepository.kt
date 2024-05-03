package shkonda.artschools.domain.repository.arts

import shkonda.artschools.domain.model.arts.ArtTypes

interface ArtTypesRepository {
    suspend fun getArtTypesByArtCategoryId(categoryId: Long): ArtTypes
}