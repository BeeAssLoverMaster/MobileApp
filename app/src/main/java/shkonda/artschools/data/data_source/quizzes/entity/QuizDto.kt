package shkonda.artschools.data.data_source.quizzes.entity

import com.google.gson.annotations.SerializedName

data class QuizDto(
    @SerializedName("quizzes")
    val quizList: List<Quiz>
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

data class HistoryQuizDto(
    @SerializedName("historyArticles")
    val historyQuizList: List<HistoryQuiz>
)

data class HistoryQuiz(
    @SerializedName("quizId")
    val quizId: Long,
    @SerializedName("title")
    val title: String,
)

data class TechniqueQuizDto(
    @SerializedName("techniqueArticles")
    val techniqueQuizList: List<TechniqueQuiz>
)

data class TechniqueQuiz(
    @SerializedName("quizId")
    val quizId: Long,
    @SerializedName("title")
    val title: String,
)

data class QuizArticleDto(
    @SerializedName("quizId")
    val quizId: Long,
    @SerializedName("artistId")
    val artistId: Long,
    @SerializedName("artistImage")
    val artistImage: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("userPoints")
    val userPoints: Int,
    @SerializedName("totalPoints")
    val totalPoints: Int,
    @SerializedName("articleImages")
    val articleImages: List<ArticleImageDto>
)

data class ArticleImageDto(
    @SerializedName("imageName")
    val imageName: String,
    @SerializedName("imageDescription")
    val imageDescription: String
)