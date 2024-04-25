package shkonda.artschools.presentation.home

import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.category.Category

sealed interface CategoriesState {
    object Loading : CategoriesState
    data class Success(val data: Categories) : CategoriesState
    data class Error(val errorMessage: String) : CategoriesState
}