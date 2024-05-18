package shkonda.artschools.presentation.main_page.quizzes.states

import shkonda.artschools.domain.model.quizzes.HistoryQuizList
import shkonda.artschools.domain.model.quizzes.QuizList
import shkonda.artschools.domain.model.quizzes.TechniqueQuizList

sealed interface QuizState {
    object Loading : QuizState
    data class Success(val quizData: QuizList) : QuizState
    data class Error(val errorMessage: String) : QuizState
}

sealed interface HistoryQuizState {
    object Loading : HistoryQuizState
    data class Success(val historyQuizData: HistoryQuizList) : HistoryQuizState
    data class Error(val errorMessage: String) : HistoryQuizState
}

sealed interface TechniqueQuizState {
    object Loading : TechniqueQuizState
    data class Success(val techniqueQuizData: TechniqueQuizList) : TechniqueQuizState
    data class Error(val errorMessage: String) : TechniqueQuizState
}