package shkonda.artschools.data.data_source.question.entity

import com.google.gson.annotations.SerializedName

data class QuestionListDto(
    @SerializedName("questions")
    val questionList: List<QuestionDto>
)

data class QuestionDto(
    @SerializedName("question")
    val question: String,
    @SerializedName("questionValue")
    val questionValue: Int,
    @SerializedName("questionImages")
    val questionImageList: List<QuestionImageDto>,
    @SerializedName("answers")
    val answerList: List<AnswerDto>

)

data class QuestionImageDto(
    @SerializedName("questionImage")
    val questionImage: String
)

data class AnswerDto(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("isCorrect")
    val isCorrect: Boolean
)

