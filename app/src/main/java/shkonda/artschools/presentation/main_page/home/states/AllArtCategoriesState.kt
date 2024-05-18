package shkonda.artschools.presentation.main_page.home.states

import shkonda.artschools.domain.model.arts.ArtCategories

sealed interface AllArtCategoriesState {
    object Loading : AllArtCategoriesState
    data class Success(val categoriesData: ArtCategories) : AllArtCategoriesState
    data class Error(val errorMessage: String) : AllArtCategoriesState
}
