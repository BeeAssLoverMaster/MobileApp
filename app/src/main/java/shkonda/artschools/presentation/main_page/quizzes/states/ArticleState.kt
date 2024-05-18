package shkonda.artschools.presentation.main_page.quizzes.states

import shkonda.artschools.domain.model.article.Articles

sealed interface ArticleState {
    object Loading : ArticleState
    data class Success(val articleData: Articles) : ArticleState
    data class Error(val errorMessage: String) : ArticleState
}