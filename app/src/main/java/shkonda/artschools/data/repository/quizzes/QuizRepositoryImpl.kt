package shkonda.artschools.data.repository.quizzes

import shkonda.artschools.data.data_source.arts.type.ArtTypesRemoteDataSource
import shkonda.artschools.data.data_source.quizzes.QuizRemoteDataSource
import shkonda.artschools.data.mappers.toQuiz
import shkonda.artschools.domain.model.quizzes.Quizzes
import shkonda.artschools.domain.repository.quizzes.QuizzesRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(private val remoteDataSource: QuizRemoteDataSource) : QuizzesRepository {
    override suspend fun getQuizzesByArtGenreId(genreId: Long): Quizzes =
        remoteDataSource.getQuizzesByArtGenreId(genreId).toQuiz()
}