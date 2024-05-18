package shkonda.artschools.presentation.main_page.art_genres

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.domain.model.arts.ArtGenre
import shkonda.artschools.domain.utils.Dimens

@Composable
fun ArtGenresScreen(
    modifier: Modifier = Modifier,
    typeId: Long,
    viewModel: ArtGenresViewModel = hiltViewModel()
) {

    LaunchedEffect(typeId) {
        viewModel.getGenresByArtTypeId(typeId)
    }

    BackHandler {
        Navigator.navigate(NavScreen.HomeScreen.route)
    }

    val artGenresState by viewModel.artGenresState.collectAsState()


    ArtGenresScreenContent(modifier, artGenresState)
}

@Composable
fun ArtGenresScreenContent(
    modifier: Modifier,
    artGenresState: ArtGenresState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Вставить из профиля topbarsection

        ArtGenresSection(
            modifier = modifier,
            artGenresState = artGenresState,
            onGenreClick = {genreId ->
                Navigator.navigate(NavScreen.QuizzesScreen.createQuizRoute(genreId))
            }
        )
    }
}

@Composable
fun ArtGenresSection(
    modifier: Modifier,
    artGenresState: ArtGenresState,
    onGenreClick: (Long) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Жанры",
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            ),
            modifier = modifier.padding(bottom = 16.dp)
        )

        when(artGenresState) {
            is ArtGenresState.Loading -> {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is ArtGenresState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 72.dp,
                        bottom = 72.dp + Dimens.AppBarDefaultHeight
                    ),
                    verticalArrangement = Arrangement.spacedBy(64.dp),
                ) {
                    items(artGenresState.genresData.genres) { genre ->
                        ArtGenreCard(
                            modifier = modifier,
                            onGenreClick = onGenreClick,
                            genreName = genre.genreName,
                            imageNameUrl = genre.imageNameUrl,
                            artGenre = genre
                        )
                    }
                }
            }
            is ArtGenresState.Error -> {
                GenresError(modifier = modifier, errorMessage = artGenresState.errorMessage)
            }
        }
    }
}

@Composable
fun ArtGenreCard(
    modifier: Modifier,
    onGenreClick: (Long) -> Unit,
    genreName: String,
    imageNameUrl: String,
    artGenre: ArtGenre
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = { onGenreClick(artGenre.id) }),
        shape = RoundedCornerShape(8)
    ) {
        AsyncImage(
            model = loadImage(context = LocalContext.current, imageUrl = imageNameUrl),
            contentDescription = "Изображение жанров",
            contentScale = ContentScale.Crop
        )
        Text(
            text = genreName,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun GenresError(modifier: Modifier, errorMessage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(96.dp),
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = null
            )
            androidx.compose.material.Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = errorMessage,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}