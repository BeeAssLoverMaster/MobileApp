package shkonda.artschools.presentation.main_page.quizzes

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.article.Article
import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtist
import shkonda.artschools.domain.model.quizzes.HistoryQuiz
import shkonda.artschools.domain.model.quizzes.Quiz
import shkonda.artschools.domain.model.quizzes.TechniqueQuiz
import shkonda.artschools.domain.utils.Dimens
import shkonda.artschools.presentation.main_page.quizzes.states.getArticleListState
import shkonda.artschools.presentation.main_page.quizzes.states.getArtistState
import shkonda.artschools.presentation.main_page.quizzes.states.getHistoryQuizListState
import shkonda.artschools.presentation.main_page.quizzes.states.getQuizListState
import shkonda.artschools.presentation.main_page.quizzes.states.getSimpleArtistState
import shkonda.artschools.presentation.main_page.quizzes.states.getTechniqueQuizListState

@Composable
fun QuizzesScreen(
    typeId: Long,
    genreId: Long,
    viewModel: QuizzesViewModel = hiltViewModel()
) {
    LaunchedEffect(genreId) {
        viewModel.getAllArtistByGenreId(genreId)
        viewModel.getQuizzesByGenreId(genreId)
        viewModel.getAllHistoryArticle(genreId)
        viewModel.getAllTechniqueArticle(genreId)
    }

    BackHandler {
        Navigator.navigate(NavScreen.ArtGenresScreen.createArtGenresRoute(typeId))
    }

    val quizList = getQuizListState(viewModel)
    val historyQuizList = getHistoryQuizListState(viewModel)
    val techniqueQuizList = getTechniqueQuizListState(viewModel)

    val simpleArtistList = getSimpleArtistState(viewModel)
    val articleList = getArticleListState(viewModel)
    val artist = getArtistState(viewModel)

    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
            QuizScreenContent(
                quizList = quizList,
                historyQuizList = historyQuizList,
                techniqueQuizList = techniqueQuizList,
                articleList = articleList,
                simpleArtistList = simpleArtistList,
                artist = artist
            )
        }

    }


}

@Composable
fun QuizScreenContent(
    quizList: List<Quiz>,
    historyQuizList: List<HistoryQuiz>,
    techniqueQuizList: List<TechniqueQuiz>,
    articleList: List<Article>,
    simpleArtistList: List<SimpleArtist>,
    artist: Artist?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item { RepresentativeQuizzes(simpleArtistList = simpleArtistList) }
        item { HistoryOfGenreQuizzes(historyQuizList = historyQuizList) }
        item { TechniqueQuizzes(techniqueQuizList = techniqueQuizList) }
    }
}

@Composable
fun RepresentativeQuizzes(
    simpleArtistList: List<SimpleArtist>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 3) - 16.dp
    Column {
        Text(
            text = "Знаковые представители ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(simpleArtistList) { quiz ->
                AsyncImage(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(shape = CircleShape)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                                start = Offset(0f, 0f), // Top-center
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ),
                            shape = CircleShape
                        )
                        .clickable {
                            Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quiz.quizId))
                        },
                    model = loadImage(context = LocalContext.current, imageUrl = quiz.imageUrl),
                    contentDescription = "Изображение профиля",
                    contentScale = ContentScale.Crop
                )

            }
        }
    }

}

@Composable
fun HistoryOfGenreQuizzes(
    historyQuizList: List<HistoryQuiz>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Column {
        Text(
            text = "История ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            historyQuizList.forEach { quiz ->
                ArticleCard(
                    title = quiz.title,
                    quizId = quiz.quizId,
                    onCardClick = { quizId ->
                        println("quizId: $quizId")
                        Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quizId))
                    }
                )
            }
        }
    }
}

@Composable
fun TechniqueQuizzes(
    modifier: Modifier = Modifier,
    techniqueQuizList: List<TechniqueQuiz>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp

    Column {
        Text(
            text = "Техники и стили исполнения ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            techniqueQuizList.forEach { quiz ->
                ArticleCard(
                    title = quiz.title,
                    quizId = quiz.quizId,
                    onCardClick = {quizId ->
                        Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quizId))
                    }
                )
            }
        }
    }
}


@Composable
fun ArticleCard(
    title: String,
    quizId: Long,
    onCardClick: (Long) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Card(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = { onCardClick(quizId) }),
        shape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 32.dp
        ), // Углы как на изображении
        elevation = 4.dp, // Добавляем тень для более красивого вида
        backgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF39386C).copy(alpha = 0.4f), Color(0xFF3D5934).copy(alpha = 0.4f)), // Градиентные цвета
                        start = Offset(0f, 0f), // Начало градиента
                        end = Offset(1000f, 1000f) // Конец градиента
                    ),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 32.dp
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF726C98), Color(0xFF66A86C)), // Цвета границы
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    ),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 32.dp
                    )
                )
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White, // Цвет текста
                fontSize = textSize,
                modifier = Modifier.align(Alignment.CenterStart), // Выравнивание текста по центру по вертикали и по левому краю по горизонтали
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun QuizCard(
    modifier: Modifier,
    onQuizClick: () -> Unit,
    articleTitle: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onQuizClick),
        shape = RoundedCornerShape(8)
    ) {
        Text(
            text = articleTitle,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun QuizError(modifier: Modifier, errorMessage: String) {
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

val fakeSimpleArtistList = listOf(
    SimpleArtist(1, 1, ""),
    SimpleArtist(2, 2, ""),
    SimpleArtist(3, 3, ""),
    SimpleArtist(4, 4, ""),
    SimpleArtist(5, 5, ""),
)

val fakeHistoryQuizList = listOf(
    HistoryQuiz(1, "Заголовок"),
    HistoryQuiz(2, "Короткий заголовок"),
    HistoryQuiz(3, "Очень длинный заголовок"),
    HistoryQuiz(3, "Экстремально длинный заголовок, который может не поместится на формочке"),
)

val fakeTechniqueQuizList = listOf(
    TechniqueQuiz(1, "Заголовок"),
    TechniqueQuiz(2, "Короткий заголовок"),
    TechniqueQuiz(3, "Очень длинный заголовок"),
    TechniqueQuiz(3, "Экстремально длинный заголовок, который может не поместится на формочке"),
    TechniqueQuiz(4, "Мега пачка чипсоу"),
)

@Preview(name = "Small Phone Preview", widthDp = 320, heightDp = 568)
@Preview(name = "Medium Phone Preview", widthDp = 360, heightDp = 640)
@Preview(name = "Large Phone Preview", widthDp = 411, heightDp = 731)
@Preview(name = "Extra Large Phone Preview", widthDp = 480, heightDp = 800)
@Composable
private fun SignInPrev() {
    FakeQuizScreenContent()
}

@Composable
fun FakeQuizScreenContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp) // Добавляем отступы между элементами
    ) {
        item {
            FakeRepresentativeQuizzes(fakeSimpleArtistList)
        }
        item {
            FakeHistoryOfGenreQuizzes()
        }
        item {
            FakeTechniqueQuizzes()
        }
    }
}

@Composable
fun FakeRepresentativeQuizzes(
    artistList: List<SimpleArtist>,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 3) - 16.dp

    Column {
        Text(
            text = "Знаковые представители ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(artistList) { quiz ->
                AsyncImage(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(shape = CircleShape)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                                start = Offset(0f, 0f), // Top-center
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ),
                            shape = CircleShape
                        )
                        .clickable {
                            Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quiz.quizId))
                        },
                    model = loadImage(
                        context = LocalContext.current,
                        imageUrl = quiz.imageUrl,
                        placeHolderAndErrorImage = R.drawable.frederic_chopin
                    ),
                    contentDescription = "Изображение профиля",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun FakeHistoryOfGenreQuizzes(
    historyQuizList: List<HistoryQuiz> = fakeHistoryQuizList
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Column {
        Text(
            text = "История ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            historyQuizList.forEach { quiz ->
                FakeArticleCard(
                    title = quiz.title
                )
            }
        }
    }
}

@Composable
fun FakeTechniqueQuizzes(
    techniqueQuizList: List<TechniqueQuiz> = fakeTechniqueQuizList
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Column {
        Text(
            text = "Техники и стили исполнения ${stringResource(id = R.string.paintingGenreArticles1)}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            techniqueQuizList.forEach { quiz ->
                FakeArticleCard(
                    title = quiz.title
                )
            }
        }
    }
}

@Composable
fun FakeArticleCard(
    modifier: Modifier = Modifier,
    title: String,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 32.dp
        ), // Углы как на изображении
        elevation = 4.dp, // Добавляем тень для более красивого вида
        backgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF39386C).copy(alpha = 0.4f), Color(0xFF3D5934).copy(alpha = 0.4f)), // Градиентные цвета
                        start = Offset(0f, 0f), // Начало градиента
                        end = Offset(1000f, 1000f) // Конец градиента
                    ),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 32.dp
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF726C98), Color(0xFF66A86C)), // Цвета границы
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    ),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 32.dp
                    )
                )
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFB39DDB), // Цвет текста
                fontSize = textSize,
                modifier = Modifier.align(Alignment.CenterStart), // Выравнивание текста по центру по вертикали и по левому краю по горизонтали
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun FakeQuizCard(modifier: Modifier = Modifier, articleTitle: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8)
    ) {
        Text(
            text = articleTitle,
            fontSize = 24.sp
        )
    }
}