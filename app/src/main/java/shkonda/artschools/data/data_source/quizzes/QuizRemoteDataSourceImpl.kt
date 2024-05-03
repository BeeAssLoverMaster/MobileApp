package shkonda.artschools.data.data_source.quizzes

import shkonda.artschools.data.data_source.arts.type.api.ArtTypesApi
import shkonda.artschools.data.data_source.quizzes.api.QuizApi
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import javax.inject.Inject

class QuizRemoteDataSourceImpl @Inject constructor(private val api: QuizApi) : QuizRemoteDataSource {
    override suspend fun getQuizzesByArtGenreId(genreId: Long): QuizDto =
        api.getQuizzesByArtGenreId(genreId)
}