package shkonda.artschools.data.repository.question

import shkonda.artschools.domain.model.question.QuestionList

interface QuestionRepository {
    suspend fun getAllQuestionAndAnswersByQuizId(quizId: Long): QuestionList
}