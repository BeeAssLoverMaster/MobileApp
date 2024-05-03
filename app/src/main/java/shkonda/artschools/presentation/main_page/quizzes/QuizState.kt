package shkonda.artschools.presentation.main_page.quizzes

import shkonda.artschools.domain.model.quizzes.Quizzes

sealed interface QuizState {
    object Loading : QuizState
    data class Success(val quizData: Quizzes) : QuizState
    data class Error(val errorMessage: String) : QuizState
}