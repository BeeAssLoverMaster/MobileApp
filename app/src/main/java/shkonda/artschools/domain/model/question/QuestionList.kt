package shkonda.artschools.domain.model.question

import com.google.gson.annotations.SerializedName

data class QuestionList(
    val questionList: List<Question>
)

data class Question(
    val question: String,
    val questionValue: Int,
    val questionImageList: List<QuestionImage>,
    val answerList: List<Answer>

)

data class QuestionImage(
    val questionImage: String
)

data class Answer(
    val answer: String,
    val isCorrect: Boolean
)