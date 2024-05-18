package shkonda.artschools.data.repository.art


import shkonda.artschools.domain.model.arts.ArtCategories
import shkonda.artschools.domain.model.arts.ArtCategory

interface ArtCategoryRepository {
    suspend fun getArtCategoryByUsername(username: String): ArtCategory
    suspend fun getAllCategories(): ArtCategories
}