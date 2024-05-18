package shkonda.artschools.data.data_source.question

import shkonda.artschools.data.data_source.question.entity.QuestionListDto

interface QuestionRemoteDataSource {
    suspend fun getAllQuestionAndAnswersByQuizId(quizId: Long): QuestionListDto
}