package shkonda.artschools.presentation.main_page.home.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.model.arts.ArtType
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.presentation.main_page.home.HomeViewModel

@Composable
fun getUserProfileState(viewModel: HomeViewModel): UserProfile? {
    val userState by viewModel.userState.collectAsState()
    return when (val state = userState) {
        is UserState.Nothing -> null
        is UserState.Success -> state.data
        is UserState.Loading -> null
        is UserState.Error -> null
    }
}

@Composable
fun getArtCategoryState(viewModel: HomeViewModel): ArtCategory? {
    val artCategoryState by viewModel.artCategoryState.collectAsState()
    return when (val state = artCategoryState) {
        is ArtCategoryState.Success -> state.categoryData
        is ArtCategoryState.Loading -> null
        is ArtCategoryState.Error -> null
    }
}

@Composable
fun getArtCategoryListState(viewModel: HomeViewModel): List<ArtCategory> {
    val allArtCategoriesState by viewModel.allArtCategoriesState.collectAsState()
    return when (val state = allArtCategoriesState) {
        is AllArtCategoriesState.Success -> state.categoriesData.artCategories
        is AllArtCategoriesState.Loading -> emptyList()
        is AllArtCategoriesState.Error -> emptyList()
    }
}

@Composable
fun getArtTypeListState(viewModel: HomeViewModel): List<ArtType> {
    val artTypesState by viewModel.artTypesState.collectAsState()
    return when (val state = artTypesState) {
        is ArtTypesState.Success -> state.typesData.types
        is ArtTypesState.Loading -> emptyList()
        is ArtTypesState.Error -> emptyList()
    }
}