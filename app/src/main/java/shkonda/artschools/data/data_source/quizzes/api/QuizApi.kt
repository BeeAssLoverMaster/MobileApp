package shkonda.artschools.data.data_source.quizzes.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto

interface QuizApi {
    @GET("api/quiz/get")
    suspend fun getQuizzesByArtGenreId(@Query("genreId") genreId: Long): QuizDto
}