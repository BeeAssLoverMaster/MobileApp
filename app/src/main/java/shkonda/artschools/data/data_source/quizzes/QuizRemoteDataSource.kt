package shkonda.artschools.data.data_source.quizzes

import shkonda.artschools.data.data_source.quizzes.entity.QuizDto

interface QuizRemoteDataSource {
    suspend fun getQuizzesByArtGenreId(genreId: Long): QuizDto
}