package shkonda.artschools.data.data_source.quizzes

import shkonda.artschools.data.data_source.quizzes.entity.HistoryQuizDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizArticleDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import shkonda.artschools.data.data_source.quizzes.entity.TechniqueQuizDto

interface QuizRemoteDataSource {
    suspend fun getQuizzesByArtGenreId(genreId: Long): QuizDto

    suspend fun getAllHistoryArticle(genreId: Long): HistoryQuizDto

    suspend fun getAllTechniqueArticle(genreId: Long): TechniqueQuizDto

    suspend fun getArticleByQuizId(token: String, quizId: Long): QuizArticleDto
}