package shkonda.artschools.presentation.main_page.quizzes.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import shkonda.artschools.domain.model.article.Article
import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtist
import shkonda.artschools.domain.model.quizzes.HistoryQuiz
import shkonda.artschools.domain.model.quizzes.Quiz
import shkonda.artschools.domain.model.quizzes.TechniqueQuiz
import shkonda.artschools.presentation.main_page.quizzes.QuizzesViewModel

@Composable
fun getQuizListState(viewModel: QuizzesViewModel):List<Quiz> {
    val quizState by viewModel.quizState.collectAsState()
    return when(val state = quizState) {
        is QuizState.Success -> state.quizData.quizList
        is QuizState.Loading -> emptyList()
        is QuizState.Error -> emptyList()
    }
}

@Composable
fun getHistoryQuizListState(viewModel: QuizzesViewModel):List<HistoryQuiz> {
    val historyQuizState by viewModel.historyQuizState.collectAsState()
    return when(val state = historyQuizState) {
        is HistoryQuizState.Success -> state.historyQuizData.quizList
        is HistoryQuizState.Loading -> emptyList()
        is HistoryQuizState.Error -> emptyList()
    }
}

@Composable
fun getTechniqueQuizListState(viewModel: QuizzesViewModel):List<TechniqueQuiz> {
    val techniqueQuizState by viewModel.techniqueQuizState.collectAsState()
    return when(val state = techniqueQuizState) {
        is TechniqueQuizState.Success -> state.techniqueQuizData.quizList
        is TechniqueQuizState.Loading -> emptyList()
        is TechniqueQuizState.Error -> emptyList()
    }
}

@Composable
fun getArticleListState(viewModel: QuizzesViewModel):List<Article> {
    val articleState by viewModel.articleState.collectAsState()
    return when(val state = articleState) {
        is ArticleState.Success -> state.articleData.articlesList
        is ArticleState.Loading -> emptyList()
        is ArticleState.Error -> emptyList()
    }
}

@Composable
fun getArtistState(viewModel: QuizzesViewModel):Artist? {
    val artistState by viewModel.artistState.collectAsState()
    return when(val state = artistState) {
        is ArtistState.Success -> state.artistData
        is ArtistState.Loading -> null
        is ArtistState.Error -> null
    }
}

@Composable
fun getSimpleArtistState(viewModel: QuizzesViewModel): List<SimpleArtist> {
    val simpleArtistState by viewModel.simpleArtistState.collectAsState()
    return when(val state = simpleArtistState) {
        is SimpleArtistState.Success -> state.simpleArtistData.artistList
        is SimpleArtistState.Loading -> emptyList()
        is SimpleArtistState.Error -> emptyList()
    }
}