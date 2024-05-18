package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.quizzes.entity.ArticleImageDto
import shkonda.artschools.data.data_source.quizzes.entity.HistoryQuizDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizArticleDto
import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import shkonda.artschools.data.data_source.quizzes.entity.TechniqueQuizDto
import shkonda.artschools.domain.model.quizzes.ArticleImage
import shkonda.artschools.domain.model.quizzes.HistoryQuiz
import shkonda.artschools.domain.model.quizzes.HistoryQuizList
import shkonda.artschools.domain.model.quizzes.Quiz
import shkonda.artschools.domain.model.quizzes.QuizArticle
import shkonda.artschools.domain.model.quizzes.QuizList
import shkonda.artschools.domain.model.quizzes.TechniqueQuiz
import shkonda.artschools.domain.model.quizzes.TechniqueQuizList

fun QuizDto.toQuiz(): QuizList {
    return QuizList(
        quizList = this.quizList.map { dto ->
            Quiz(
                id = dto.id,
                artGenreId = dto.artGenreId,
                articleId = dto.articleId,
                articleTitle = dto.articleTitle
            )
        }
    )
}

fun HistoryQuizDto.toHistoryQuiz(): HistoryQuizList {
    return HistoryQuizList(
        quizList = this.historyQuizList.map { dto ->
            HistoryQuiz(
                quizId = dto.quizId,
                title = dto.title
            )
        }
    )
}

fun TechniqueQuizDto.toTechniqueQuiz(): TechniqueQuizList {
    return TechniqueQuizList(
        quizList = this.techniqueQuizList.map { dto ->
            TechniqueQuiz(
                quizId = dto.quizId,
                title = dto.title
            )
        }
    )
}

fun QuizArticleDto.toQuizArticle(): QuizArticle {
    return QuizArticle(
        quizId = this.quizId,
        artistId = this.artistId,
        artistImage = this.artistImage,
        title = this.title,
        text = this.text,
        userPoints = this.userPoints,
        totalPoints = this.totalPoints,
        articleImages = this.articleImages.map { it.toArticleImage() }
    )
}

fun ArticleImageDto.toArticleImage(): ArticleImage {
    return ArticleImage(
        imageName = this.imageName,
        imageDescription = this.imageDescription
    )
}
