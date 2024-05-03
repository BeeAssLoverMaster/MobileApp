package shkonda.artschools.presentation.main_page.quizzes

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import shkonda.artschools.R
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.domain.utils.Dimens

@Composable
fun QuizzesScreen(
    modifier: Modifier = Modifier,
    genreId: Long,
    viewModel: QuizzesViewModel = hiltViewModel()
) {
    LaunchedEffect(genreId) {
        viewModel.getQuizzesByGenreId(genreId)
    }

    BackHandler {
        Navigator.navigate(NavScreen.HomeScreen.route)
    }

    val quizState by viewModel.quizState.collectAsState()

    QuizScreenContent(modifier, quizState)
}

@Composable
fun QuizScreenContent(
    modifier: Modifier,
    quizState: QuizState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizSection(
            modifier = modifier,
            quizState = quizState,
            onGenresClick = {
                //Сюда добавить Navigator...
            }
        )
    }
}

@Composable
fun QuizSection(
    modifier: Modifier,
    quizState: QuizState,
    onGenresClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Статьи",
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            ),
            modifier = modifier.padding(bottom = 16.dp)
        )

        when(quizState) {
            is QuizState.Loading -> {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is QuizState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 72.dp,
                        bottom = 72.dp + Dimens.AppBarDefaultHeight
                    ),
                    verticalArrangement = Arrangement.spacedBy(64.dp),
                ) {
                    items(quizState.quizData.quizzesList) {
                        QuizCard(
                            modifier = modifier,
                            onQuizClick = { /*TODO*/ },
                            articleTitle = it.articleTitle,
                        )
//                        CategoryCard(
//                            modifier = modifier,
//                            onClick = {},
//                            categoryName = it.categoryName
//                        )
                    }
                }
            }
            is QuizState.Error -> {
                QuizError(modifier = modifier, errorMessage = quizState.errorMessage)
            }
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