package shkonda.artschools.data.repository.art

import shkonda.artschools.domain.model.arts.ArtTypes

interface ArtTypesRepository {
    suspend fun getArtTypesByArtCategoryId(categoryId: Long): ArtTypes
}