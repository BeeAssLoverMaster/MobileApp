package shkonda.artschools.data.repository.question

import shkonda.artschools.data.data_source.articles.ArticleRemoteDataSource
import shkonda.artschools.data.data_source.question.QuestionRemoteDataSource
import shkonda.artschools.data.mappers.toQuestionList
import shkonda.artschools.domain.model.question.QuestionList
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(private val remoteDataSource: QuestionRemoteDataSource): QuestionRepository {
    override suspend fun getAllQuestionAndAnswersByQuizId(quizId: Long): QuestionList =
        remoteDataSource.getAllQuestionAndAnswersByQuizId(quizId).toQuestionList()
}