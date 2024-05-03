package shkonda.artschools.presentation.main_page.art_genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.domain.usecase.arts.GetArtGenresUseCase
import javax.inject.Inject


@HiltViewModel
class ArtGenresViewModel @Inject constructor(
    private val getArtGenresUseCase: GetArtGenresUseCase
) : ViewModel() {
    private val _artGenresState = MutableStateFlow<ArtGenresState>(ArtGenresState.Loading)
    val artGenresState = _artGenresState.asStateFlow()

    fun getGenresByArtTypeId(typeId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getArtGenresUseCase(typeId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _artGenresState.value = ArtGenresState.Loading
                }

                is Response.Success -> {
                    _artGenresState.value = ArtGenresState.Success(genresData = response.data)
                }

                is Response.Error -> {
                    _artGenresState.value = ArtGenresState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}