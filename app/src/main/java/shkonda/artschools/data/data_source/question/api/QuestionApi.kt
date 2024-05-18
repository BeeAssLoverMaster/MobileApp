package shkonda.artschools.data.data_source.question.api

import retrofit2.http.GET
import retrofit2.http.Query
import shkonda.artschools.data.data_source.question.entity.QuestionListDto

interface QuestionApi {
    @GET("api/question/get_all")
    suspend fun getAllQuestionAndAnswersByQuizId(@Query("quizId") quizId: Long): QuestionListDto
}