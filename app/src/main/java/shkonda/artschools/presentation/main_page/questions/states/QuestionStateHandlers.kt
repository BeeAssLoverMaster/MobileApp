package shkonda.artschools.presentation.main_page.questions.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.question.Question
import shkonda.artschools.domain.model.question.QuestionList
import shkonda.artschools.presentation.main_page.questions.QuestionViewModel
import shkonda.artschools.presentation.main_page.quizzes.QuizzesViewModel
import shkonda.artschools.presentation.main_page.quizzes.states.ArtistState

@Composable
fun getQuestionListState(viewModel: QuestionViewModel): List<Question> {
    val questionListState by viewModel.questionListState.collectAsState()
    return when(val state = questionListState) {
        is QuestionListState.Success -> state.questionListData.questionList
        is QuestionListState.Loading -> emptyList()
        is QuestionListState.Error -> emptyList()
    }
}