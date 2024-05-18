package shkonda.artschools.data.repository.quiz

import shkonda.artschools.domain.model.quizzes.HistoryQuizList
import shkonda.artschools.domain.model.quizzes.QuizArticle
import shkonda.artschools.domain.model.quizzes.QuizList
import shkonda.artschools.domain.model.quizzes.TechniqueQuizList

interface QuizRepository {
    suspend fun getQuizzesByArtGenreId(genreId: Long): QuizList

    suspend fun getAllHistoryArticle(genreId: Long): HistoryQuizList
    suspend fun getAllTechniqueArticle(genreId: Long): TechniqueQuizList

    suspend fun getArticleByQuizId(token: String, quizId: Long): QuizArticle
}