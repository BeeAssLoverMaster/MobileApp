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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    modifier: Modifier = Modifier,
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


    QuizScreenContent(
        modifier = modifier,
        quizList = quizList,
        historyQuizList = historyQuizList,
        techniqueQuizList = techniqueQuizList,
        articleList = articleList,
        simpleArtistList = simpleArtistList,
        artist = artist
    )
}

@Composable
fun QuizScreenContent(
    modifier: Modifier,
    quizList: List<Quiz>,
    historyQuizList: List<HistoryQuiz>,
    techniqueQuizList: List<TechniqueQuiz>,
    articleList: List<Article>,
    simpleArtistList: List<SimpleArtist>,
    artist: Artist?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Статьи",
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            ),
            modifier = modifier.padding(bottom = 16.dp)
        )
        RepresentativeQuizzes(simpleArtistList = simpleArtistList, artist = artist)
        HistoryOfGenreQuizzes(historyQuizList = historyQuizList)
        TechniqueQuizzes(techniqueQuizList = techniqueQuizList)
    }
}

@Composable
fun RepresentativeQuizzes(
    modifier: Modifier = Modifier,
    simpleArtistList: List<SimpleArtist>,
    artist: Artist?
) {
    val context = LocalContext.current
    Column {
        Text(text = "Представители искусства")

        LazyRow(
            modifier = modifier.background(Brush.horizontalGradient(listOf(Sunset, Grapefruit)))
        ) {
            items(simpleArtistList) {quiz ->
                AsyncImage(
                    modifier = modifier
                        .size(72.dp)
                        .clip(shape = CircleShape)
                        .border(
                            border = BorderStroke(
                                width = 2.dp, brush = Brush.horizontalGradient(
                                    colors = listOf(Sunset, Grapefruit)
                                )
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
    modifier: Modifier = Modifier,
    historyQuizList: List<HistoryQuiz>
) {
    val context = LocalContext.current
    Column {
        Text(text = "Истории искусства")

        LazyColumn(
            contentPadding = PaddingValues(
                top = 72.dp,
                bottom = 72.dp + Dimens.AppBarDefaultHeight
            ),
            verticalArrangement = Arrangement.spacedBy(64.dp),
        ) {
            items(historyQuizList) { quiz ->
                ArticleCard(
                    modifier = modifier,
                    title = quiz.title,
                    quizId = quiz.quizId,
                    onCardClick = {quizId ->
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
    Column {
        Text(text = "Техники и стили исполнения")

        LazyColumn(
            contentPadding = PaddingValues(
                top = 72.dp,
                bottom = 72.dp + Dimens.AppBarDefaultHeight
            ),
            verticalArrangement = Arrangement.spacedBy(64.dp),
        ) {
            items(techniqueQuizList) { quiz ->
                ArticleCard(
                    modifier = modifier,
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
    modifier: Modifier,
    title: String,
    quizId: Long,
    onCardClick: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = { onCardClick(quizId) }),
        shape = RoundedCornerShape(8)
    ) {
        Text(
            text = title,
            fontSize = 24.sp
        )
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