package shkonda.artschools.presentation.main_page.questions.states

import shkonda.artschools.domain.model.question.QuestionList

sealed interface QuestionListState {
    object Loading : QuestionListState
    data class Success(val questionListData: QuestionList) : QuestionListState
    data class Error(val errorMessage: String) : QuestionListState
}