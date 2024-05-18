package shkonda.artschools.presentation.main_page.home.states

import kotlinx.coroutines.flow.StateFlow

data class CompositeState(
    val userState: StateFlow<UserState>,
    val artCategoryState: StateFlow<ArtCategoryState>,
    val allArtCategoriesState: StateFlow<AllArtCategoriesState>
)