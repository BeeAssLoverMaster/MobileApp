package shkonda.artschools.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.domain.usecase.genre.GetGenresUseCase
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    // TODO: вставить usecase
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {
    private val _genresState = MutableStateFlow<GenresState>(GenresState.Loading)
    val genresState = _genresState.asStateFlow()

//    init {
//        getGenres()
//    }

    fun getGenres(categoryId: String) = viewModelScope.launch(Dispatchers.IO) {
        getGenresUseCase(categoryId = categoryId).collect() { response ->
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