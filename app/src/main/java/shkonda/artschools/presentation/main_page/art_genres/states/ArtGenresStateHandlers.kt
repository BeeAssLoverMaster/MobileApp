package shkonda.artschools.presentation.main_page.art_genres.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import shkonda.artschools.domain.model.arts.ArtGenre
import shkonda.artschools.presentation.main_page.art_genres.ArtGenresState
import shkonda.artschools.presentation.main_page.art_genres.ArtGenresViewModel

@Composable
fun getArtGenreState(viewModel: ArtGenresViewModel): List<ArtGenre> {
    val allArtGenresState by viewModel.artGenresState.collectAsState()
    return when(val state = allArtGenresState) {
        is ArtGenresState.Success -> state.genresData.genres
        is ArtGenresState.Loading -> emptyList()
        is ArtGenresState.Error -> emptyList()
    }
}