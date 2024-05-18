package shkonda.artschools.presentation.main_page.article.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import shkonda.artschools.domain.model.article.ArtistArticle
import shkonda.artschools.domain.model.quizzes.QuizArticle
import shkonda.artschools.presentation.main_page.article.ArticleViewModel

@Composable
fun getQuizArticleState(viewModel: ArticleViewModel): QuizArticle? {
    val quizArticleState by viewModel.quizArticleState.collectAsState()
    return when(val state = quizArticleState) {
        is QuizArticleState.Success -> state.quizArticleData
        is QuizArticleState.Loading -> null
        is QuizArticleState.Error -> null
    }
}

@Composable
fun getBioArticleState(viewModel: ArticleViewModel): ArtistArticle? {
    val bioArticleState by viewModel.bioArticleState.collectAsState()
    return when(val state = bioArticleState) {
        is BioArticleState.Success -> state.bioArticleData
        is BioArticleState.Loading -> null
        is BioArticleState.Error -> null
    }
}