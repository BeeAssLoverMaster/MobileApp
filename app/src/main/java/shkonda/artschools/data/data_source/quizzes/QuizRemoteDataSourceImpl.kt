package shkonda.artschools.data.data_source.quizzes

import shkonda.artschools.data.data_source.arts.type.api.ArtTypesApi
import shkonda.artschools.data.data_source.quizzes.api.QuizApi
import shkonda.artschools.data.data_source.quizzes.entity.HistoryQuizDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizArticleDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import shkonda.artschools.data.data_source.quizzes.entity.TechniqueQuizDto
import javax.inject.Inject

class QuizRemoteDataSourceImpl @Inject constructor(private val api: QuizApi) : QuizRemoteDataSource {
    override suspend fun getQuizzesByArtGenreId(genreId: Long): QuizDto =
        api.getQuizzesByArtGenreId(genreId)

    override suspend fun getAllHistoryArticle(genreId: Long): HistoryQuizDto =
        api.getAllHistoryArticle(genreId)

    override suspend fun getAllTechniqueArticle(genreId: Long): TechniqueQuizDto =
        api.getAllTechniqueArticle(genreId)

    override suspend fun getArticleByQuizId(token: String, quizId: Long): QuizArticleDto =
        api.getArticleByQuizId(token, quizId)
}