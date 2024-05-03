package shkonda.artschools.presentation.main_page.home.states

import shkonda.artschools.domain.model.arts.ArtCategory

sealed interface ArtCategoryState {
    object Loading : ArtCategoryState
    data class Success(val categoryData: ArtCategory) : ArtCategoryState
    data class Error(val errorMessage: String) : ArtCategoryState
}