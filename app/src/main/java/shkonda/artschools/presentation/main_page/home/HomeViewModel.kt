package shkonda.artschools.presentation.main_page.home

import android.content.SharedPreferences
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getToken
import shkonda.artschools.domain.usecase.arts.GetArtCategoryUseCase
import shkonda.artschools.domain.usecase.arts.GetArtTypesUseCase
import shkonda.artschools.domain.usecase.user.GetUserProfileUseCase
import shkonda.artschools.presentation.main_page.home.states.AllArtCategoriesState
import shkonda.artschools.presentation.main_page.home.states.ArtCategoryState
import shkonda.artschools.presentation.main_page.home.states.ArtTypesState
import shkonda.artschools.presentation.main_page.home.states.CompositeState
import shkonda.artschools.presentation.main_page.home.states.UpdateArtCategoryUserState
import shkonda.artschools.presentation.main_page.home.states.UserState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getArtCategoryUseCase: GetArtCategoryUseCase,
    private val getArtTypesUseCase: GetArtTypesUseCase,
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()

    private val _updateUserCategoryState =
        MutableStateFlow<UpdateArtCategoryUserState>(UpdateArtCategoryUserState.Loading)
    val updateUserCategoryState = _updateUserCategoryState.asStateFlow()

    private val _artCategoryState = MutableStateFlow<ArtCategoryState>(ArtCategoryState.Loading)
    val artCategoryState = _artCategoryState.asStateFlow()

    val compositeState by derivedStateOf { CompositeState(_userState, artCategoryState, allArtCategoriesState) }

    private val _allArtCategoriesState =
        MutableStateFlow<AllArtCategoriesState>(AllArtCategoriesState.Loading)
    val allArtCategoriesState = _allArtCategoriesState.asStateFlow()

    private val _artTypesState = MutableStateFlow<ArtTypesState>(ArtTypesState.Loading)
    val artTypesState = _artTypesState.asStateFlow()

    private var token: String? = null

    init {
        getToken()
        getUserProfile()
        getAllCategories()
    }

    private fun getToken() {
        token = sharedPreferences.getToken()
        println("token: $token")
    }

    private fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _userState.value = UserState.Loading
                }

                is Response.Success -> {
                    val username = response.data.username
                    getCategory(username)

                    _userState.value = UserState.Success(data = response.data)
                }

                is Response.Error -> {
                    _userState.value = UserState.Error(errorMessage = response.errorMessage)
                }

                else -> {}
            }
        }
    }

    fun updateUserArtCategory(artCategoryId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(
            token = "Bearer $token",
            artCategoryId = artCategoryId
        ).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _updateUserCategoryState.value = UpdateArtCategoryUserState.Loading
                }

                is Response.Success -> {
                    _updateUserCategoryState.value = UpdateArtCategoryUserState.Success
                    getUserProfile()
                }

                is Response.Error -> {
                    _updateUserCategoryState.value =
                        UpdateArtCategoryUserState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    private fun getCategory(username: String) = viewModelScope.launch(Dispatchers.IO) {
        getArtCategoryUseCase(username).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _artCategoryState.value = ArtCategoryState.Loading
                }

                is Response.Success -> {
                    val categoryId = response.data.id
                    getTypes(categoryId)
                    _artCategoryState.value = ArtCategoryState.Success(categoryData = response.data)
                }

                is Response.Error -> {
                    _artCategoryState.value =
                        ArtCategoryState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    private fun getAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        getArtCategoryUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _artCategoryState.value = ArtCategoryState.Loading
                }

                is Response.Success -> {
                    _allArtCategoriesState.value =
                        AllArtCategoriesState.Success(categoriesData = response.data)
                }

                is Response.Error -> {
                    _artCategoryState.value =
                        ArtCategoryState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    private fun getTypes(categoryId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getArtTypesUseCase(categoryId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _artTypesState.value = ArtTypesState.Loading
                }

                is Response.Success -> {
                    println("response.data:" + response.data)
                    _artTypesState.value = ArtTypesState.Success(typesData = response.data)
                }

                is Response.Error -> {
                    _artTypesState.value =
                        ArtTypesState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
    /*
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
        }*/
}