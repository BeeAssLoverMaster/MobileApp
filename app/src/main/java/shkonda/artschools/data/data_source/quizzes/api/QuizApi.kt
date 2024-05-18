package shkonda.artschools.data.data_source.quizzes.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import shkonda.artschools.data.data_source.quizzes.entity.HistoryQuizDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizArticleDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import shkonda.artschools.data.data_source.quizzes.entity.TechniqueQuizDto

interface QuizApi {
    @GET("api/quiz/get")
    suspend fun getQuizzesByArtGenreId(@Query("genreId") genreId: Long): QuizDto

    @GET("api/quiz/get_by_genre/history_article")
    suspend fun getAllHistoryArticle(@Query("genreId") genreId: Long): HistoryQuizDto

    @GET("api/quiz/get_by_genre/technique_article")
    suspend fun getAllTechniqueArticle(@Query("genreId") genreId: Long): TechniqueQuizDto

    @GET("api/quiz/get_by_quiz/article")
    suspend fun getArticleByQuizId(
        @Header("Authorization") token: String,
        @Query("quizId") quizId: Long
    ): QuizArticleDto
}