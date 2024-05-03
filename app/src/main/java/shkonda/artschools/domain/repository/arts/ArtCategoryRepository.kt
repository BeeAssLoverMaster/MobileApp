package shkonda.artschools.domain.repository.arts


import shkonda.artschools.domain.model.arts.ArtCategory

interface ArtCategoryRepository {

//    suspend fun getCategories() : Categories
    suspend fun getArtCategoryByUsername(username: String) : ArtCategory

}