package shkonda.artschools.data.data_source.question

import shkonda.artschools.data.data_source.question.api.QuestionApi
import shkonda.artschools.data.data_source.question.entity.QuestionListDto
import javax.inject.Inject

class QuestionRemoteDataSourceImpl @Inject constructor(private val api: QuestionApi): QuestionRemoteDataSource {
    override suspend fun getAllQuestionAndAnswersByQuizId(quizId: Long): QuestionListDto =
        api.getAllQuestionAndAnswersByQuizId(quizId)
}