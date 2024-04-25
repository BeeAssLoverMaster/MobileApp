package shkonda.artschools.domain.repository


import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.genre.Genres

interface CategoriesRepository {

//    suspend fun getCategories() : Categories
    suspend fun getCategories() : Categories

    suspend fun getGenresByCategory(categoryId: String) : Genres
}