package shkonda.artschools.domain.model.quizzes

import com.google.gson.annotations.SerializedName

data class QuizList(
    val quizList: List<Quiz>
)

data class Quiz(
    val id: Long,
    val artGenreId: Long,
    val articleId: Long,
    val articleTitle: String
)

data class HistoryQuizList(
    val quizList: List<HistoryQuiz>
)

data class HistoryQuiz(
    val quizId: Long,
    val title: String
)

data class TechniqueQuizList(
    val quizList: List<TechniqueQuiz>
)

data class TechniqueQuiz(
    val quizId: Long,
    val title: String
)

data class QuizArticle(
    val quizId: Long,
    val artistId: Long,
    val artistImage: String,
    val title: String,
    val text: String,
    val userPoints: Int,
    val totalPoints: Int,
    val articleImages: List<ArticleImage>
)

data class ArticleImage(
    val imageName: String,
    val imageDescription: String
)