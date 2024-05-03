//package shkonda.artschools.presentation.themes
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import shkonda.artschools.core.common.Response
//import shkonda.artschools.domain.usecase.theme.GetArtistUseCase
//import shkonda.artschools.presentation.main_page.genres.GenresState
//import shkonda.artschools.presentation.themes.ThemeStates.ArtistState
//import javax.inject.Inject
//
//@HiltViewModel
//class ThemesViewModel @Inject constructor(
//    private val getArtistUseCase: GetArtistUseCase
//) : ViewModel() {
//    private val _artistState = MutableStateFlow<ArtistState>(ArtistState.Loading)
//    val artistState = _artistState.asStateFlow()
//
//    fun getArtists(genreId: String) = viewModelScope.launch(Dispatchers.IO) {
//        getArtistUseCase(genreId = genreId).collect() { response ->
//            when (response) {
//                is Response.Loading -> {
//                    _artistState.value = ArtistState.Loading
//                }
//
//                is Response.Success -> {
//                    _artistState.value = ArtistState.Success(data = response.data)
//                }
//
//                is Response.Error -> {
//                    _artistState.value = ArtistState.Error(errorMessage = response.errorMessage)
//                }
//            }
//        }
//    }
//}
