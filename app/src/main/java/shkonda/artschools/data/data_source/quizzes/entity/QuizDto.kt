package shkonda.artschools.data.data_source.quizzes.entity

import com.google.gson.annotations.SerializedName

data class QuizDto(
    @SerializedName("quizzes")
    val quizzesList: List<Quiz>
)

data class Quiz(
    @SerializedName("id")
    val id: Long,
    @SerializedName("artGenreId")
    val artGenreId: Long,
    @SerializedName("articleId")
    val articleId: Long,
    @SerializedName("articleTitle")
    val articleTitle: String
)