package shkonda.artschools.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getToken
import shkonda.artschools.domain.usecase.category.GetCategoriesUseCase
import shkonda.artschools.domain.usecase.category.GetGenresByCategoryUseCase
import shkonda.artschools.domain.usecase.user.GetUserProfileUseCase
import shkonda.artschools.presentation.genres.GenresState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getGenresByCategoryUseCase: GetGenresByCategoryUseCase,

    ) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private val _genresState = MutableStateFlow<GenresState>(GenresState.Loading)
    val genresState = _genresState.asStateFlow()

    private var token: String? = null

    init {
        getToken()
        getUserProfile()
        getCategories()
    }

    private fun getToken() {
        token = sharedPreferences.getToken()
    }

    private fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _userState.value = UserState.Loading
                }

                is Response.Success -> {
                    _userState.value = UserState.Success(data = response.data)
                }

                is Response.Error -> {
                    _userState.value = UserState.Error(errorMessage = response.errorMessage)
                }

                else -> {}
            }
        }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        getCategoriesUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _categoriesState.value = CategoriesState.Loading
                }

                is Response.Success -> {
                    _categoriesState.value = CategoriesState.Success(data = response.data)
                }

                is Response.Error -> {
                    _categoriesState.value =
                        CategoriesState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun onCategorySelected(categoryId: String) {
        viewModelScope.launch {
//            _genresState.value = GenresState.Loading
            getGenresByCategoryUseCase(categoryId).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _genresState.value = GenresState.Loading
                    }

                    is Response.Success -> {
                        _genresState.value = GenresState.Success(data = response.data)
                    }

                    is Response.Error -> {
                        _genresState.value = GenresState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }
}