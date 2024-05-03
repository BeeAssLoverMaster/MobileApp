//package shkonda.artschools.presentation.themes
//
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.AsyncImage
//import shkonda.artschools.R
//import shkonda.artschools.core.common.loadImage
//import shkonda.artschools.core.navigation.NavScreen
//import shkonda.artschools.core.navigation.Navigator
//import shkonda.artschools.core.ui.components.CustomLoadingSpinner
//import shkonda.artschools.core.ui.theme.Grapefruit
//import shkonda.artschools.core.ui.theme.Sunset
//import shkonda.artschools.domain.model.theme.Artist
//import shkonda.artschools.domain.model.theme.Artists
//import shkonda.artschools.presentation.themes.ThemeStates.ArtistState
//
//@Composable
//fun ThemesScreen(
//    modifier: Modifier = Modifier,
//    genreId: String,
//    viewModel: ThemesViewModel = hiltViewModel()
//) {
//
//    LaunchedEffect(genreId) {
//        viewModel.getArtists(genreId)
//    }
//    val artistState by viewModel.artistState.collectAsState()
//
//    BackHandler {
//        Navigator.navigate(NavScreen.GenresScreen.route)
//    }
//
//    ThemesScreenContent(modifier = modifier, artistState = artistState)
//}
//
//@Composable
//fun ThemesScreenContent(
//    modifier: Modifier,
//    artistState: ArtistState
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // TODO: Возможно понадобиться вставить ещё один Column
//        Representatives(modifier = modifier, artistState = artistState)
//        Spacer(modifier = modifier.padding(16.dp))
//        HistoryBlock()
//        // TODO: Остальные блоки
//    }
//}
//
//val fakeArtist = listOf(
//    Artist("1", "FIO", "userImage", ""),
//    Artist("2", "FIO", "userImage", ""),
//    Artist("3", "FIO", "userImage", ""),
//    Artist("4", "FIO", "userImage", ""),
//    Artist("5", "FIO", "userImage", ""),
//)
//val fakeArtists = Artists(artists = ArrayList(fakeArtist))
//val fakeArtistState = ArtistState.Success(fakeArtists)
//
//@Preview(showBackground = true, widthDp = 360)
//@Composable
//private fun RepresentativesPrev() {
//    Representatives(modifier = Modifier, artistState = fakeArtistState)
//}
//@Composable
//fun Representatives(
//    modifier: Modifier,
//    artistState: ArtistState
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        Text(
//            text = "Представители жанра",
//            style = MaterialTheme.typography.h3.copy(
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colors.primaryVariant
//            ),
//            modifier = modifier.padding(bottom = 16.dp)
//        )
//
//        when (artistState) {
//            is ArtistState.Loading -> {
//                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                    CustomLoadingSpinner()
//                }
//            }
//
//            is ArtistState.Success -> {
//                LazyRow(
//                    modifier = modifier.background(Brush.horizontalGradient(listOf(Sunset, Grapefruit)))
//                ) {
//                    items(artistState.data.artists) { artist ->
//                        Box(
//                            modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)
//                        ) {
//                            AsyncImage(
//                                modifier = modifier
//                                    .size(72.dp)
//                                    .clip(shape = CircleShape)
//                                    .border(
//                                        border = BorderStroke(
//                                            width = 2.dp, brush = Brush.horizontalGradient(
//                                                colors = listOf(Sunset, Grapefruit)
//                                            )
//                                        ),
//                                        shape = CircleShape
//                                    ),
//                                model = loadImage(context = LocalContext.current, imageUrl = artist.artistImg),
//                                contentDescription = "Изображение категорий",
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
//                }
//            }
//
//            is ArtistState.Error -> {
//                ThemesError(modifier = modifier, errorMessage = artistState.errorMessage)
//            }
//        }
//    }
//
//}
//
//@Composable
//fun HistoryBlock() {
//    /// TODO: реализовать исторический блок
//
//}
//
//@Composable
//private fun ThemesError(modifier: Modifier, errorMessage: String) {
//    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//        Column(
//            modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                modifier = modifier.size(96.dp),
//                painter = painterResource(id = R.drawable.error_image),
//                contentDescription = null
//            )
//            Text(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                text = errorMessage,
//                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
//                color = MaterialTheme.colors.primaryVariant,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
