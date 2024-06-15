package shkonda.artschools.presentation.main_page.art_genres

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.model.arts.ArtGenre
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.domain.utils.Dimens
import shkonda.artschools.presentation.main_page.art_genres.states.getArtGenreState
import shkonda.artschools.presentation.main_page.home.HomeViewModel
import shkonda.artschools.presentation.main_page.home.TopBarSection
import shkonda.artschools.presentation.main_page.home.TypeCard
import shkonda.artschools.presentation.main_page.home.states.getArtCategoryListState
import shkonda.artschools.presentation.main_page.home.states.getArtCategoryState
import shkonda.artschools.presentation.main_page.home.states.getUserProfileState
@Composable
fun ArtGenresScreen(modifier: Modifier = Modifier, typeId: Long, viewModelArt: ArtGenresViewModel = hiltViewModel(), viewModelHome: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(typeId) { viewModelArt.getGenresByArtTypeId(typeId) }
    BackHandler { Navigator.navigate(NavScreen.HomeScreen.route) }
    val user = getUserProfileState(viewModelHome)
    val artCategory = getArtCategoryState(viewModelHome)
    val artCategoryList = getArtCategoryListState(viewModelHome)
    val artGenreList = getArtGenreState(viewModelArt)
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.background), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)))
            ArtGenresScreenContent(modifier, user, artCategory, artCategoryList, artGenreList, typeId, viewModelHome)
        }
    }
}
@Composable
fun ArtGenresScreenContent(modifier: Modifier, user: UserProfile?, artCategory: ArtCategory?, artCategoryList: List<ArtCategory>, artGenreList: List<ArtGenre>, typeId: Long, viewModelHome: HomeViewModel) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            TopBarSection(modifier = modifier, user = user, artCategory = artCategory, artCategoryList = artCategoryList, viewModel = viewModelHome)
        }
        Spacer(modifier = Modifier.height(4.dp))
        ArtGenresSection(modifier = modifier, artGenreList = artGenreList, onGenreClick = { genreId -> Navigator.navigate(NavScreen.QuizzesScreen.createQuizRoute(genreId))}, typeId = typeId)
    }
}
@Composable
fun ArtGenresSection(modifier: Modifier, artGenreList: List<ArtGenre>, onGenreClick: (Long) -> Unit, typeId: Long) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val cardSize = (screenWidth / 2) - 16.dp
    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        val text = when (typeId) {
            1L -> "Жанры ${stringResource(id = R.string.painting)}"
            2L -> "Жанры ${stringResource(id = R.string.graphic)}"
            3L -> "Жанры ${stringResource(id = R.string.architecture)}"
            else -> ""
        }
        Text(text = text, style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = textSize), modifier = modifier.padding(bottom = 8.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(artGenreList) {genre -> TypeCard(modifier = modifier, typeTitle = genre.genreName, typeImageUrl = genre.imageNameUrl, onCategoryClick = onGenreClick, item = genre, cardSize = cardSize, getId = { it.id }) }
        }
    }
}