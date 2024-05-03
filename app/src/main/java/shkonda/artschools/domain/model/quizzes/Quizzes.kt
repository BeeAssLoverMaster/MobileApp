package shkonda.artschools.domain.model.quizzes

import com.google.gson.annotations.SerializedName

data class Quizzes(
    val quizzesList: List<Quiz>
)

data class Quiz(
    val id: Long,
    val artGenreId: Long,
    val articleId: Long,
    val articleTitle: String
)
