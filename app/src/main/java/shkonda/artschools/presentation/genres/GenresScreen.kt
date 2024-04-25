package shkonda.artschools.presentation.genres

import android.app.Activity
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import shkonda.artschools.R
import shkonda.artschools.core.ui.OnBackPressed
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.domain.model.genre.Genre
import shkonda.artschools.domain.model.genre.Genres
import shkonda.artschools.domain.utils.Dimens

@Composable
fun GenresScreen(
    categoryId: String,
    modifier: Modifier = Modifier,
    viewModel: GenresViewModel = hiltViewModel()
    ) {
    LaunchedEffect(categoryId) {
        viewModel.getGenres(categoryId)
    }
    println(categoryId)
    val genresState by viewModel.genresState.collectAsState()

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    GenresScreenContent(modifier, genresState)
}

val fakeCategoriesList = listOf(
    Genre("1", "Test1"),
    Genre("2", "Test2"),
    Genre("3", "Test3"),
    Genre("4", "Test4"),
)
val fakeCategories = Genres(genres = ArrayList(fakeCategoriesList))
val fakeState = GenresState.Success(fakeCategories)

@Preview
@Composable
private fun GenresScreenPrev() {
    QuizGenresSection(
        modifier = Modifier,
        genresState = fakeState,
        onGenresClick = {}
    )
}

@Composable
fun GenresScreenContent(
    modifier: Modifier,
    genresState: GenresState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Вставить из профиля topbarsection

        QuizGenresSection(
            modifier = modifier,
            genresState = genresState,
            onGenresClick = {
                //Сюда добавить Navigator...
            })
    }
}

@Composable
fun QuizGenresSection(
    modifier: Modifier,
    genresState: GenresState,
    onGenresClick: () -> Unit
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

        when(genresState) {
            is GenresState.Loading -> {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is GenresState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 72.dp,
                        bottom = 72.dp + Dimens.AppBarDefaultHeight
                    ),
                    verticalArrangement = Arrangement.spacedBy(64.dp),
                ) {
                    items(genresState.data.genres) {
                        GenreCard(
                            modifier = modifier,
                            onGenreClick = { /*TODO*/ },
                            categoryName = it.genreName,
//                            categoryImage = it.genreImage
                        )
//                        CategoryCard(
//                            modifier = modifier,
//                            onClick = {},
//                            categoryName = it.categoryName
//                        )
                    }
                }
            }
            is GenresState.Error -> {
                GenresError(modifier = modifier, errorMessage = genresState.errorMessage)
            }
        }
    }
}

@Composable
fun GenreCard(
    modifier: Modifier,
    onGenreClick: () -> Unit,
    categoryName: String,
//    categoryImage: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onGenreClick),
        shape = RoundedCornerShape(8)
    ) {
//        AsyncImage(
//            model = loadImage(context = LocalContext.current, imageUrl = categoryImage),
//            contentDescription = "Изображение жанров",
//            contentScale = ContentScale.Crop
//        )
        Text(
            text = categoryName,
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


















































































