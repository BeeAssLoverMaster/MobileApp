package shkonda.artschools.presentation.main_page.article.states

import shkonda.artschools.domain.model.article.ArtistArticle
import shkonda.artschools.domain.model.quizzes.QuizArticle

sealed interface QuizArticleState {
    object Loading : QuizArticleState
    data class Success(val quizArticleData: QuizArticle) : QuizArticleState
    data class Error(val errorMessage: String) : QuizArticleState
}

sealed interface BioArticleState {
    object Loading : BioArticleState
    data class Success(val bioArticleData: ArtistArticle) : BioArticleState
    data class Error(val errorMessage: String) : BioArticleState
}