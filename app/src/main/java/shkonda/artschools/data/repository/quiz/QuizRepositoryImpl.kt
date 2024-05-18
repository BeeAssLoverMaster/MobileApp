package shkonda.artschools.data.repository.quiz

import shkonda.artschools.data.data_source.quizzes.QuizRemoteDataSource
import shkonda.artschools.data.mappers.toHistoryQuiz
import shkonda.artschools.data.mappers.toQuiz
import shkonda.artschools.data.mappers.toQuizArticle
import shkonda.artschools.data.mappers.toTechniqueQuiz
import shkonda.artschools.domain.model.quizzes.HistoryQuizList
import shkonda.artschools.domain.model.quizzes.QuizArticle
import shkonda.artschools.domain.model.quizzes.QuizList
import shkonda.artschools.domain.model.quizzes.TechniqueQuizList
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(private val remoteDataSource: QuizRemoteDataSource) :
    QuizRepository {
    override suspend fun getQuizzesByArtGenreId(genreId: Long): QuizList =
        remoteDataSource.getQuizzesByArtGenreId(genreId).toQuiz()

    override suspend fun getAllHistoryArticle(genreId: Long): HistoryQuizList =
        remoteDataSource.getAllHistoryArticle(genreId).toHistoryQuiz()

    override suspend fun getAllTechniqueArticle(genreId: Long): TechniqueQuizList =
        remoteDataSource.getAllTechniqueArticle(genreId).toTechniqueQuiz()

    override suspend fun getArticleByQuizId(token: String, quizId: Long): QuizArticle =
        remoteDataSource.getArticleByQuizId(token, quizId).toQuizArticle()
}